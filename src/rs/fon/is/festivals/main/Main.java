package rs.fon.is.festivals.main;

import java.util.ArrayList;
import java.util.Collection;

import rs.fon.is.festivals.domain.Festival;
import rs.fon.is.festivals.domain.Genre;
import rs.fon.is.festivals.domain.MusicArtist;
import rs.fon.is.festivals.parser.FestivalParser;
import rs.fon.is.festivals.persistence.DataModelManager;
import rs.fon.is.festivals.services.QueryExecutor;
import rs.fon.is.festivals.util.Constants;

public class Main {

	public static void main(String[] args) {

		//ArrayList<String> ids = FestivalParser.getAllFestivalsIDs();
		//Festival festival = FestivalParser.parse(ids.get(0));
		//DataModelManager.getInstance().save(festival);
		//System.out.println(festival.getUri());
		Festival festival = (Festival) DataModelManager.getInstance().load("http://is.fon.rs/rdfFestivals/Festival/7c63efa3-b468-45a1-8043-185b7823ada5");
		System.out.println(festival.getLocation().getLocationName());
		DataModelManager.getInstance().closeDataModel();
	}

}
