package rs.fon.is.festivals.rest;

import java.util.Collection;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import rs.fon.is.festivals.domain.Festival;
import rs.fon.is.festivals.services.FestivalServiceImpl;


@Path("/festivals")
public class FestivalsRest {
	
	private FestivalServiceImpl festivalsRepository = new FestivalServiceImpl();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getFestivalsWithGenre(@DefaultValue("") @QueryParam("genre") String genre){
		Collection<Festival> festivals = festivalsRepository.getFestivals(genre);
		if (festivals != null && !festivals.isEmpty()) {
			JsonArray festivalsArray = new JsonArray();
			for (Festival festival : festivals) {
				JsonObject festivalJson = FestivalsJsonParser.serializeFestival(festival);
				festivalsArray.add(festivalJson);
			}
			return festivalsArray.toString();
		}
		throw new WebApplicationException(Response.Status.NO_CONTENT);
	}

}
