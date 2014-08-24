package rs.fon.is.festivals.main;

import java.util.ArrayList;
import java.util.Collection;

import rs.fon.is.festivals.domain.Festival;
import rs.fon.is.festivals.domain.Genre;
import rs.fon.is.festivals.domain.MusicArtist;
import rs.fon.is.festivals.parser.FestivalParser;
import rs.fon.is.festivals.persistence.DataModelManager;
import rs.fon.is.festivals.services.FestivalServiceImpl;
import rs.fon.is.festivals.services.QueryExecutor;
import rs.fon.is.festivals.util.Constants;

public class Main {

	public static void main(String[] args) {

		ArrayList<String> ids = FestivalParser.getAllFestivalsIDs();
		for (int i = 0; i < 10; i++) {
			Festival festival = FestivalParser.parse(ids.get(i));
			DataModelManager.getInstance().save(festival.getLocation());
			DataModelManager.getInstance().save(festival.getInterval());
			for (Genre genre : festival.getGenres()) {
				DataModelManager.getInstance().save(genre);
			}
			for (MusicArtist artist : festival.getLineup()) {
				DataModelManager.getInstance().save(artist);
			}
			DataModelManager.getInstance().save(festival);
		}

		DataModelManager.getInstance().closeDataModel();

	}

}
