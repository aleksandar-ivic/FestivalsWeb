package rs.fon.is.festivals.rest;

import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import rs.fon.is.festivals.services.GenreServiceImpl;

import com.google.gson.JsonArray;

@Path("/genres")
public class GenresRest {
	
	private GenreServiceImpl genresRepository = new GenreServiceImpl();

	@GET
	@Produces("application/json; charset=UTF-8")
	public String getAllGenres() {
		HashMap<String, Integer> genresMap = genresRepository.getAllGenres();
		
		if (genresMap != null && !genresMap.isEmpty()) {
			JsonArray genresArray = GenresJsonParser.serializeGenre(genresMap);
			
			return genresArray.toString();
		}
		return "{}";
	}

}
