package rs.fon.is.festivals.main;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import rs.fon.is.festivals.domain.Festival;
import rs.fon.is.festivals.domain.Genre;
import rs.fon.is.festivals.domain.MusicArtist;
import rs.fon.is.festivals.parser.FestivalParser;
import rs.fon.is.festivals.persistence.DataModelManager;
import rs.fon.is.festivals.services.FestivalServiceImpl;
import rs.fon.is.festivals.services.QueryExecutor;
import rs.fon.is.festivals.util.Constants;
import rs.fon.is.festivals.util.URIGenerator;
import rs.fon.is.festivals.util.Util;

public class Main {

	public static void main(String[] args) throws Exception {
		
		/*ArrayList<String> ids = FestivalParser.getAllFestivalsIDs();
		for (int i = 0; i < 20; i++) {
			Festival festival = FestivalParser.parse(ids.get(i));
			//System.out.println(festival.getUri());
			DataModelManager.getInstance().save(festival);
			DataModelManager.getInstance().save(festival.getLocation());
			//System.out.println(festival.getLocation().getUri());
			DataModelManager.getInstance().save(festival.getInterval());
			//System.out.println(festival.getInterval().getUri());
			for (MusicArtist artist : festival.getLineup()) {
				DataModelManager.getInstance().save(artist);
				//System.out.println(artist.getUri());
			}
		}*/

		DataModelManager.getInstance().printout();
		DataModelManager.getInstance().closeDataModel();
		/*HashMap<String, Genre> mapOfGenres = Util.loadMap();
		for (Genre genre : mapOfGenres.values()) {
			System.out.println(genre.getUri());
		}*/
	}

}
