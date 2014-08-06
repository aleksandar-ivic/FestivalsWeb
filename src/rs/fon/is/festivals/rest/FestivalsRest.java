package rs.fon.is.festivals.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;


@Path("/festivals")
public class FestivalsRest {
	
	@GET
	public String getFestivalsWithGenre(){
		return "radi";
	}

}
