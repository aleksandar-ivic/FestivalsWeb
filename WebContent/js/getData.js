var google_map;
var info_window;
var mapOptions;
var festivals;

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
				div.innerHTML = div.innerHTML +'       '
						+ '<a href="#map" class="btn-genre" id="'
						+ genres[i].title
						+ '" onclick="getFestivalsWithGenre(id);">'
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

function getFestivalsWithDate() {
	var dateFrom = document.getElementById('datepicker1').value;
	var dateTo = document.getElementById('datepicker2').value;
	if (dateFrom == '') {
		alert("You must set date from!");
		return;
	}
	if (dateTo == '') {
		alert("You must set date to!");
		return;
	}
	var json = new Array();
	json = getData("festivals?genre=&dateFrom=" + dateFrom +"&dateTo=" + dateTo);
	if (json.length > 0) {
		setupMap(JSON.stringify(json));
	} else {
		alert("There are no festivals that start on: " + date);
	}

}

function setupMap(json) {

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
		if (location != 'Unknown' && location != '') {
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

function getFestivalsWithGenre(selectValue) {
	var link = document.getElementById(selectValue);
	link.color = 'red';
	var json = getData("festivals?genre=" + selectValue + "&?date=");
	setupMap(JSON.stringify(json));
}