package rs.fon.is.festivals.services;

import java.util.HashMap;
import java.util.TreeMap;

import rs.fon.is.festivals.domain.Genre;

public interface GenreService {
	
	public HashMap<String, Integer> getAllGenres();
	public Genre getGenre(String uri);

}
