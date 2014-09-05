package rs.fon.is.festivals.rest;

import java.util.HashMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class GenresJsonParser {
	
	public static JsonArray serializeGenre(HashMap<String, Integer> genresMap){
		JsonArray genreArrayJson = new JsonArray();
		for (String genre : genresMap.keySet()) {
			JsonObject genreJsonObject = new JsonObject();
			
			//genreJsonObject.addProperty("uri", genre.getUri().toString());
			genreJsonObject.addProperty("title", genre);
			genreJsonObject.addProperty("numOfFestWithGenre", genresMap.get(genre));
			
			genreArrayJson.add(genreJsonObject);
		}
		
		return genreArrayJson;
	}

}
