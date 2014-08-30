package rs.fon.is.festivals.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;

import rs.fon.is.festivals.domain.Festival;
import rs.fon.is.festivals.domain.Genre;
import rs.fon.is.festivals.persistence.DataModelManager;
import rs.fon.is.festivals.util.Constants;
import rs.fon.is.festivals.util.Util;

public class GenreServiceImpl implements GenreService{
	
	QueryExecutor queryExecutor;

	public GenreServiceImpl(){
		queryExecutor = new QueryExecutor();
	}

	@Override
	public HashMap<Genre, Integer> getAllGenres() {
		StringBuffer query = new StringBuffer();
		// prefix part
		query.append("PREFIX dc:<" + Constants.DC + ">");
		query.append("PREFIX mo:<" + Constants.MO + "> \n");
		query.append("PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n");
		query.append("PREFIX ns:<" + Constants.NS + "> \n");
		// select part
		query.append("SELECT DISTINCT ?genre \n");

		// where
		query.append("WHERE\n{\n");
		query.append(" \t?genre rdf:type mo:Genre;\n");
		query.append(" \tdc:title ?title.");
		query.append("}\n");
		query.append("ORDER BY DESC(?title)");
		QueryExecutor queryExecutor = new QueryExecutor();
		Collection<String> queryResults = queryExecutor
				.executeOneVariableSelectSparqlQuery(query.toString(),
						"genre", DataModelManager.getInstance().getModel());
		HashMap<Genre, Integer> genresMap = new HashMap<>();
		
		if (queryResults != null && !queryResults.isEmpty()) {
			for (String uri : queryResults) {
				Genre genre = getGenre(uri);
				FestivalServiceImpl fsi = new FestivalServiceImpl();
				//Collection<Festival> festivals = fsi.getFestivals(genre.getTitle());
				int numOfFestsWithGenre = fsi.getNumberOfFestivalsWithGenre(genre.getTitle());
				genresMap.put(genre, numOfFestsWithGenre);
				
			}
			HashMap<Genre, Integer> genresMapSorted = Util.sortMap(genresMap);
			
			
			return genresMapSorted;
		}
		return null;
	}

	@Override
	public Genre getGenre(String uri) {
		return (Genre) DataModelManager.getInstance().load(uri);
	}

}
