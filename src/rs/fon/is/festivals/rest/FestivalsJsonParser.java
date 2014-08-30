package rs.fon.is.festivals.rest;

import java.text.SimpleDateFormat;

import rs.fon.is.festivals.domain.Festival;
import rs.fon.is.festivals.domain.Genre;
import rs.fon.is.festivals.domain.MusicArtist;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class FestivalsJsonParser {

	public static JsonObject serializeFestival(Festival festival) {
		JsonObject festivalJson = new JsonObject();


		festivalJson.addProperty("uri", festival.getUri().toString());
		festivalJson.addProperty("festivalName", festival.getFestivalName());

		JsonObject intervalJson = new JsonObject();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		if (festival.getInterval() != null) {
			if (festival.getInterval().getStart() != null) {
				intervalJson.addProperty("start", sdf.format(festival.getInterval()
						.getStart()));
			} else {
				intervalJson.addProperty("start", "");
			}
			if (festival.getInterval().getEnd() != null) {
				intervalJson.addProperty("end", sdf.format(festival.getInterval()
						.getEnd()));
			} else {
				intervalJson.addProperty("end", "");
			}
			festivalJson.add("interval", intervalJson);
		}

		JsonObject locationJson = new JsonObject();
		if (festival.getLocation() != null) {
			locationJson.addProperty("locationName", festival.getLocation()
					.getLocationName());
			locationJson.addProperty("lat", festival.getLocation().getLat());
			locationJson.addProperty("lng", festival.getLocation().getLng());
			festivalJson.add("location", locationJson);
		}else{
			festivalJson.addProperty("location", "");
		}
		
		JsonArray artistsArray = new JsonArray();
		if (!festival.getLineup().isEmpty()) {
			for (MusicArtist artist : festival.getLineup()) {
				JsonObject artistJson = new JsonObject();
				artistJson.addProperty("artistName", artist.getName());
				JsonArray genresArray = new JsonArray();
				if (!artist.getGenres().isEmpty()) {					
					for (String genre : artist.getGenres()) {
						JsonObject genreJson = new JsonObject();
						genreJson.addProperty("genreTitle", genre);
						genresArray.add(genreJson);
					}
					artistJson.add("genres", genresArray);					
				}else{
					artistJson.add("genres", new JsonArray());
				}
				artistsArray.add(artistJson);
			}
		}
		festivalJson.add("lineup", artistsArray);

		return festivalJson;
	}

}
