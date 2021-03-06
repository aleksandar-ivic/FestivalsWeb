var google_map;
var info_window;
var mapOptions;
var festivals;
var firstClick = true;
var selectedGenres = '';
var generatedMarkers = new Array();

function changeLinkColor(id) {
	var links = document.getElementsByTagName('a');
	var selectedLink = '';

	for (var i = 0; i < links.length; i++) {
		if (links[i].id == id && links[i].className != 'btn-genre selected') {
			selectedLink = document.getElementById(links[i].id);
			selectedLink.className += ' selected';
		}
	}
}

function resetCriteria() {
	var links = document.getElementsByTagName('a');
	for (var i = 0; i < links.length; i++) {
		if (links[i].className == 'btn-genre selected') {
			links[i].className = 'btn-genre';
		}
	}
	selectedGenres = '';
	document.getElementById('datepicker1').value = '';
	document.getElementById('datepicker2').value = '';
	initialize();
	var div = document.getElementById("festInfo");
	div.style.display = "none";
}

function addItem(json) {
	var mostPopularGenres = [ 'techno', 'house', 'minimal', 'trance', 'indie',
			'heavy metal', 'electronic', 'drum n bass', 'jazz', 'rap',
			'hip hop', 'alternative', '80s', '90s', 'deep house',
			'progressive', 'rock', 'punk', 'metal', 'pop', 'rnb' ];
	var genresStringify = JSON.stringify(json);
	var genres = JSON.parse(genresStringify);
	var div = document.getElementById("genres");
	div.style.color = "#02324A";
	for (var i = 0; i < genres.length; i++) {
		for (g in mostPopularGenres) {
			if (mostPopularGenres[g] == genres[i].title) {
				div.innerHTML = div.innerHTML + '       '
						+ '<a href="#map" class="btn-genre" id="'
						+ genres[i].title + '" onclick="selectGenre(id);">'
						+ genres[i].title + '(' + genres[i].numOfFestWithGenre
						+ ")</a>";

			}
		}

	}
}

function getDataGenre() {
	var div = document.getElementById("festInfo");
	div.style.display = "none";
	var json = getData("genres");
	addItem(json);
}

function initialize() {
	mapOptions = {
		zoom : 3,
		center : new google.maps.LatLng(50.086161, 14.411519999999996),
		mapTypeId : google.maps.MapTypeId.ROADMAP
	};
	google_map = new google.maps.Map(document.getElementById('map-canvas'),
			mapOptions);
	info_window = new google.maps.InfoWindow({
		content : 'loading'
	});
}
google.maps.event.addDomListener(window, 'load', initialize);

function getFestivals() {
	initialize();
	var dateFrom = document.getElementById('datepicker1').value;
	var dateTo = document.getElementById('datepicker2').value;
	if (selectedGenres == '' && dateFrom == '' && dateTo == '') {
		alert("You must choose at least one criteria!");
		return;
	}
	if ((dateFrom != '' && dateTo == '') || (dateFrom == '' && dateTo != '')) {
		alert("You must set both values for interval!");
		return;
	}
	if (dateFrom != '' && dateTo != '' && selectedGenres != '') {
		var json = new Array();
		json = getData("festivals?genre=" + selectedGenres + "&dateFrom="
				+ dateFrom + "&dateTo=" + dateTo);
		if (json.length > 0) {
			setupMap(JSON.stringify(json));
		} else {
			alert("There are no festivals in this interval and with selected genres!");
			initialize();
		}
	}
	if (dateFrom != '' && dateTo != '' && selectedGenres == '') {
		var json = new Array();
		json = getData("festivals?genre=&dateFrom=" + dateFrom + "&dateTo="
				+ dateTo);
		if (json.length > 0) {
			setupMap(JSON.stringify(json));

		} else {
			alert("There are no festivals in this interval!");
			initialize();
		}
	}
	if (dateFrom == '' && dateTo == '' && selectedGenres != '') {
		var json = new Array();
		json = getData("festivals?genre=" + selectedGenres
				+ "&?dateFrom=&dateTo=");
		if (json.length > 0) {
			setupMap(JSON.stringify(json));
		}

	}
}

function setupMap(json) {
	/*
	 * mapOptions = { zoom : 3, center : new google.maps.LatLng(50.086161,
	 * 14.411519999999996), mapTypeId : google.maps.MapTypeId.ROADMAP };
	 * google_map = new google.maps.Map(document.getElementById('map-canvas'),
	 * mapOptions); info_window = new google.maps.InfoWindow({ content :
	 * 'loading' });
	 */

	festivals = JSON.parse(json);

	var t = [];
	var x = [];
	var y = [];
	var h = [];
	var f = [];
	var d1 = [];
	var d2 = [];
	var l = [];
	for (var j = 0; j < festivals.length; j++) {
		var locationName = festivals[j].location.locationName;
		l.push(locationName);
		var lineup = [];
		var lineupJSON = JSON.parse(JSON.stringify(festivals[j].lineup));
		lineupJSON.forEach(function(artist) {
			var artistWithGenres = artist.artistName.toUpperCase();
			lineup.push(artistWithGenres);
		});
		f.push(lineup);
		var start = festivals[j].interval.start;
		d1.push(start);
		var end = festivals[j].interval.end;
		d2.push(end);
		var festivalName = festivals[j].festivalName;

		t.push(festivalName);
		var lat = festivals[j].location.lat;
		x.push(lat);
		var lng = festivals[j].location.lng;
		y.push(lng);
		h.push('<p><strong>' + festivalName + '</strong></br>Location: '
				+ locationName + '</p>');

	}
	var index = 0;
	for (var i = 0; i < t.length; i++) {
		var pin = new google.maps.Marker({
			map : google_map,
			animation : google.maps.Animation.DROP,
			title : t[i],
			position : new google.maps.LatLng(x[i], y[i]),
			html : h[i],
			text : f[i],
			begin : d1[i],
			end : d2[i],
			location : l[i]
		});
		google.maps.event
				.addListener(
						pin,
						'click',
						function(link) {
							index = i;
							info_window.setContent(this.html);
							info_window.open(google_map, this);
							var selection = document.getElementById("festInfo");
							selection.style.display = "inline";
							var div = document.getElementById("artists");
							var h2 = document.getElementById('lineup');
							h2.style.color = "#424342";
							h2.style.textAlign = "center";
							div.innerHTML = "<h1 id='festName'>" + this.title
									+ "</h1></br><h2 id='lineup'>Location: "
									+ this.location + "</br></h2>";
							div.innerHTML = div.innerHTML
									+ "<h2 id='lineup'>Starts at: "
									+ this.begin + "</br>Ends at: " + this.end
									+ " </br></br>Lineup:</h2>";
							this.text
									.forEach(function(artist) {
										div.innerHTML = div.innerHTML
												+ '<a href="#festInfo" class="btn btn-dark btn-lg btn-genre" id="'
												+ artist + '">' + artist
												+ "</a>	";
									});
							var link = document.createElement('a');
							link.href = "#festivalInfo";
							link.style.diplay = "none";
							document.body.appendChild(link);
							link.click();

						});
	}
}

function selectGenre(selectValue) {
	// var json = getData("festivals?genre=" + selectValue +
	// "&?dateFrom=&dateTo=" );
	// setupMap(JSON.stringify(json));
	selectedGenres += selectValue + ',';
	changeLinkColor(selectValue);
	// document.getElementById('datepicker1').value = '';
	// document.getElementById('datepicker2').value = '';
}