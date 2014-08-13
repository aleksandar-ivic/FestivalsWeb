package rs.fon.is.festivals.main;

import java.util.ArrayList;

import rs.fon.is.festivals.domain.Festival;
import rs.fon.is.festivals.domain.Genre;
import rs.fon.is.festivals.domain.MusicArtist;
import rs.fon.is.festivals.parser.FestivalParser;
import rs.fon.is.festivals.persistence.DataModelManager;

public class Main {

	public static void main(String[] args) {

		ArrayList<String> ids = FestivalParser.getAllFestivalsIDs();
		for (String id : ids) {
			Festival festival = FestivalParser.parse(id);
			DataModelManager.getInstance().save(festival);
		}
		DataModelManager.getInstance().closeDataModel();

	}

}
