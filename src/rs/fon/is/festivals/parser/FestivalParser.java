package rs.fon.is.festivals.parser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;

import org.json.JSONArray;
import org.json.JSONObject;

import de.umass.lastfm.Artist;
import de.umass.lastfm.Event;
import de.umass.lastfm.Venue;
import rs.fon.is.festivals.domain.Festival;
import rs.fon.is.festivals.domain.Genre;
import rs.fon.is.festivals.domain.Interval;
import rs.fon.is.festivals.domain.Location;
import rs.fon.is.festivals.domain.MusicArtist;
import rs.fon.is.festivals.persistence.DataModelManager;
import rs.fon.is.festivals.util.LineupWithGenres;
import rs.fon.is.festivals.util.URIGenerator;
import rs.fon.is.festivals.util.Util;
import rs.fon.is.festivals.util.XMLParser;

public class FestivalParser {

	private static String key = "26440e0193813621bf98c49ab9fd67cc";
	private static String user = "thecoa4";
	private static String URL = "http://ws.audioscrobbler.com/2.0/?method=geo.getEvents&location=Europe&distance=100&festivalsonly=1&api_key=26440e0193813621bf98c49ab9fd67cc&page=";

	public static ArrayList<String> getAllFestivalsIDs() {
		ArrayList<String> ids = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			ids.addAll(XMLParser.parseDocument(URL + i));
		}
		return ids;
	}

	public static Festival parse(String festivalID) {
		Festival festival = new Festival();

		// getting info about festival from last.fm
		Event event = Event.getInfo(festivalID, key);
		String festivalName = event.getTitle();
		festival.setFestivalName(festivalName);

		// getting start and end date
		Interval interval = parseInterval(event);
		if (interval != null) {
			festival.setInterval(interval);
		}

		// getting lat and lng of the festival city
		Location location = parseLocation(event);
		if (location != null) {
			festival.setLocation(location);
		}

		// getting lineup and genres
		LineupWithGenres lineupWithGenres = parseArtists(event);
		festival.setLineup(lineupWithGenres.getArtists());
		festival.setGenres(lineupWithGenres.getGenres());

		try {
			festival.setUri(URIGenerator.generate(festival));
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
		return festival;
	}

	private static LineupWithGenres parseArtists(Event event) {
		LineupWithGenres lineupWithGenres = new LineupWithGenres();
		Collection<MusicArtist> lineup = new ArrayList<>();
		Collection<Genre> genresSet = new LinkedHashSet<>();
		Collection<String> artists = event.getArtists();
		for (String artistName : artists) {
			Artist artist = Artist.getInfo(artistName, key);
			MusicArtist musicArtist = new MusicArtist(artistName);

			Collection<Genre> MAgenres = parseGenres(artist);
			genresSet.addAll(MAgenres);
			for (Genre genre : MAgenres) {
				musicArtist.getGenres().add(genre.getTitle());
			}
			try {
				musicArtist.setUri(URIGenerator.generate(musicArtist));
			} catch (URISyntaxException e) {
				e.printStackTrace();
				//return new LineupWithGenres();
			}
			lineup.add(musicArtist);
			lineupWithGenres.setArtists(lineup);
			lineupWithGenres.setGenres(genresSet);
		}
		return lineupWithGenres;
	}

	private static Collection<Genre> parseGenres(Artist artist) {
		Collection<Genre> genres = new ArrayList<>();
		Collection<String> tags = artist.getTags();
		HashMap<String, Genre> mapOfGenres = Util.loadMap();
		for (String tag : tags) {
			Genre genre = new Genre(tag);
			try {
				Genre g = Util.seeIfGenreExists(genre);
				if (g == null) {
					genre.setUri(URIGenerator.generate(genre));
					//System.out.println("Genre ne postoji:  " + genre.getUri());
					DataModelManager.getInstance().save(genre);
					mapOfGenres.put(tag, genre);
					try {
						Util.saveMap(mapOfGenres);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					genre = g;
					//System.out.println("Postoji zanr: "  + g.getUri());
					genre.setUri(g.getUri());
					//System.out.println("Genre " + genre.getUri());
					DataModelManager.getInstance().save(genre);
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			genres.add(genre);
		}
		return genres;
	}

	private static Location parseLocation(Event event) {
		try {
			Venue venue = event.getVenue();
			String city = venue.getCity();
			double[] latlng = getLatAndLng(city);
			Location location = new Location(city, latlng[0], latlng[1]);
			location.setUri(URIGenerator.generate(location));
			return location;
		} catch (Exception ex) {
			ex.printStackTrace();
			Location location = new Location("Unknown", 0, 0);
			try {
				location.setUri(URIGenerator.generate(location));
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			return location;
		}
	}

	private static Interval parseInterval(Event event) {
		try {
			Interval interval = new Interval();
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
			sdf.setLenient(false);
			Date start = event.getStartDate();			
			try {
				String startFormat = sdf.format(start);
				start = null;
				start = sdf.parse(startFormat);
				interval.setStart(start);
				Date end = event.getEndDate();
				if (end == null) {
					end = start;
				}else{
					end = sdf.parse(sdf.format(end));
				}
				interval.setEnd(end);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			interval.setUri(URIGenerator.generate(interval));
			return interval;
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
			return null;
		}

	}

	private static double[] getLatAndLng(String city) throws Exception {
		double[] latlng = new double[2];
		String url = "http://api.geonames.org/searchJSON?q="
				+ URLEncoder.encode(city, "utf-8") + "&maxRows=1&username="
				+ user;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");

		int responseCode = con.getResponseCode();
		StringBuffer response = new StringBuffer();
		if (responseCode == 200) {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream(), "UTF-8"));
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			JSONObject json = new JSONObject(response.toString());
			double lat = 0;
			double lng = 0;
			JSONArray jsonArray = json.getJSONArray("geonames");
			for (int i = 0; i < jsonArray.length(); i++) {
				lat = jsonArray.getJSONObject(i).getDouble("lat");
				lng = jsonArray.getJSONObject(i).getDouble("lng");
			}
			latlng[0] = lat;
			latlng[1] = lng;
		}
		return latlng;
	}
}
