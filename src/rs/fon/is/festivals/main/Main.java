package rs.fon.is.festivals.main;


import java.util.ArrayList;
import rs.fon.is.festivals.domain.Festival;
import rs.fon.is.festivals.parser.FestivalParser;
import rs.fon.is.festivals.persistence.DataModelManager;

public class Main {

	public static void main(String[] args){

		ArrayList<String> ids = FestivalParser.getAllFestivalsIDs();
		Festival festival = FestivalParser.parse(ids.get(0));
		System.out.println(festival.getUri());
		System.out.println(festival.getLocation().getUri());
		System.out.println(festival.getInterval().getUri());
		//DataModelManager.getInstance().save(festival);
		//DataModelManager.getInstance().closeDataModel();

	}

}
