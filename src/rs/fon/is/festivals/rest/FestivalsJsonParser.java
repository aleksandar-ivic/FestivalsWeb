package rs.fon.is.festivals.rest;

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
		if (festival.getInterval() != null) {
			if (festival.getInterval().getStart() != null) {
				intervalJson.addProperty("start", festival.getInterval()
						.getStart().toString());
			} else {
				intervalJson.addProperty("start", "");
			}
			if (festival.getInterval().getEnd() != null) {
				intervalJson.addProperty("end", festival.getInterval()
						.getStart().toString());
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
					JsonObject genreJson = new JsonObject();
					for (Genre genre : artist.getGenres()) {
						genreJson.addProperty("genreTitle", genre.getTitle());
						genresArray.add(genreJson);
					}
					artistJson.add("genres", genresArray);					
				}
				artistsArray.add(artistJson);
			}
			
		}

		return festivalJson;
	}

}
