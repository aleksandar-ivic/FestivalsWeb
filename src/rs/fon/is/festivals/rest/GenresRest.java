package rs.fon.is.festivals.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/genres")
public class GenresRest {
	
	@GET
	public String getAllGenres(){
		return "{genres: ["
				+ "        [31, 'hip hop', 5],"
				+ "        [32, 'rock', 4]"
				+ "    ]}";
	}

}
