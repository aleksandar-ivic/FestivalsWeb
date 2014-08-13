package rs.fon.is.festivals.main;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JEditorPane;
import javax.swing.JFrame;

import org.json.JSONException;
import org.json.JSONObject;

import rs.fon.is.festivals.domain.Festival;
import rs.fon.is.festivals.parser.FestivalParser;
import rs.fon.is.festivals.persistence.DataModelManager;
import rs.fon.is.festivals.rest.FestivalsJsonParser;
import rs.fon.is.festivals.util.Util;

public class Main {

	public static void main(String[] args) throws IOException, JSONException {

		ArrayList<String> ids = FestivalParser.getAllFestivalsIDs();
		Festival festival = FestivalParser.parse(ids.get(0));
		System.out.println(festival.getFestivalName());
		DataModelManager.getInstance().save(festival);
		//DataModelManager.getInstance().printout();
		DataModelManager.getInstance().closeDataModel();
		//DataModelManager.getInstance().save(festival);
		//DataModelManager.getInstance().closeDataModel();
		//allFestivals.add(festival);
		//Util.writeFestivalsToJson(festival, 1);

	}

}
