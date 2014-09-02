package rs.fon.is.festivals.rest;

import java.util.Collection;
import java.util.Date;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.Encoded;
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
	@Produces("application/json; charset=UTF-8")
	public String getFestivalsWithGenre(
			@DefaultValue("") @QueryParam("genre") String genre, @DefaultValue("") @QueryParam("dateFrom") String dateFrom, @DefaultValue("") @QueryParam("dateTo") String dateTo) {
		if (!genre.equals("")) {
			Collection<Festival> allFestivals = festivalsRepository
					.getFestivals(genre, "", "");
			if (allFestivals != null && !allFestivals.isEmpty()) {
				JsonArray festivalsArray = new JsonArray();
				for (Festival festival : allFestivals) {
					JsonObject festivalJson = FestivalsJsonParser
							.serializeFestival(festival);
					festivalsArray.add(festivalJson);
				}
				return festivalsArray.toString();
			}
		}

		if (!dateFrom.equals("") && !dateTo.equals("")) {
			Collection<Festival> allFestivals = festivalsRepository
					.getFestivals("", dateFrom, dateTo);
			if (allFestivals != null && !allFestivals.isEmpty()) {
				JsonArray festivalsArray = new JsonArray();
				for (Festival festival : allFestivals) {
					JsonObject festivalJson = FestivalsJsonParser
							.serializeFestival(festival);
					festivalsArray.add(festivalJson);
				}
				return festivalsArray.toString();
			}
		}
		return "{}";
	}
}
