package rs.fon.is.festivals.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import rs.fon.is.festivals.domain.Festival;
import rs.fon.is.festivals.domain.Genre;

public class Util {
	
	private static int num = 0;
	private static String festivalsJsonString = "";

	public static void saveMap(HashMap<String, Genre> mapOfGenres) {
		try {
			FileOutputStream fileOut = new FileOutputStream("tmp\\genres.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(mapOfGenres);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	public static HashMap<String, Genre> loadMap() {
		HashMap<String, Genre> mapOfGenres = new HashMap<String, Genre>();
		FileInputStream fileIn;
		try {
			fileIn = new FileInputStream("tmp\\genres.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			mapOfGenres = (HashMap<String, Genre>) in.readObject();
			in.close();
			fileIn.close();
		} catch (FileNotFoundException e) {
			new File("tmp\\genres.ser");
			// loadMap();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mapOfGenres;
	}

	public static Genre seeIfGenreExists(Genre genre) throws Exception {
		HashMap<String, Genre> mapOfGenres = loadMap();
		for (Genre g : mapOfGenres.values()) {
			if (g.getTitle().equals(genre.getTitle())) {
				return g;
			}
		}
		return null;
	}

	public static void writeLocationJSON(Festival festival) {
		String jsonString = "{\"coordinates\":[{\"city\":\"" + festival.getLocation().getLocationName() +"\", \"lat\":"
				+ festival.getLocation().getLat() + ", \"lng\":"
				+ festival.getLocation().getLng() + "}]}";
		System.out.println(jsonString);
		JSONObject json;
		FileWriter file = null;
		try {
			json = new JSONObject(jsonString);

			file = new FileWriter(
					"C:\\Bitnami\\wampstack-5.4.28-0\\apache2\\htdocs\\coordinates.json");

			file.write(json.toString());

		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				file.flush();
				file.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public static void writeGenresToJson(){
		String jsonString = "[{\"genres\":[";
		HashMap<String, Genre> mapOfGenres = loadMap();
		for (String genre: mapOfGenres.keySet()) {
			jsonString += "{\"genre\":\"" + genre + "\"},";
		}
		
		
		String newJson = (String) jsonString.subSequence(0, 795);
		newJson += "]}]";
		System.out.println(newJson);
		JSONArray json;
		FileWriter file = null;
		try {
			json = new JSONArray(newJson);

			file = new FileWriter(
					"C:\\Bitnami\\wampstack-5.4.28-0\\apache2\\htdocs\\genres.json");

			file.write(json.toString());

		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				//file.flush();
				file.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	public static void writeFestivalsToJson(Festival festival, int numOfFestivals){
		StringBuffer jsonString = new StringBuffer(festivalsJsonString);
		if (num == 0) {
			jsonString.append("[{\"festivals\":[");
			jsonString.append("{\"festival\":[\"festivalName\":\""+festival.getFestivalName() + "\",");
			jsonString.append("\"interval\":[\"start\":\"" + festival.getInterval().getStart() + "\",");
			jsonString.append("\"end\":\"" + festival.getInterval() + "\"],");
			jsonString.append("}]");
			num++;
		}else if (num == numOfFestivals) {
			jsonString.append("}]");
		}else{
			jsonString.append("{\"festival\":[\"festivalName\":\""+festival.getFestivalName() + "\"");
		}
		System.out.println(jsonString.toString());
	}

}
