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

import com.google.gson.JsonObject;

import riotcmd.json;
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

}
