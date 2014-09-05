package rs.fon.is.festivals.services;

import java.util.HashMap;

import rs.fon.is.festivals.domain.Genre;
import rs.fon.is.festivals.persistence.DataModelManager;
import rs.fon.is.festivals.util.Constants;
import rs.fon.is.festivals.util.Util;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;

public class GenreServiceImpl implements GenreService{
	
	QueryExecutor queryExecutor;

	public GenreServiceImpl(){
		queryExecutor = new QueryExecutor();
	}

	@Override
	public HashMap<String, Integer> getAllGenres() {
		String query = 
				"PREFIX dc:<" + Constants.DC + "> \n" +
				"PREFIX mo:<" + Constants.MO + "> \n" + 
				"PREFIX rdf:<" + Constants.RDF + "> \n" + 
				
				"SELECT DISTINCT ?title (COUNT(?festival) as ?nfestivals) \n" + 
				"WHERE { \n" + 
					"?festival rdf:type mo:Festival; \n" +
						"\t mo:genre ?genre . \n" +
				
					"?genre rdf:type mo:Genre ; \n" + 
						"\t dc:title ?title . \n" + 
				"} \n" +
					
				"GROUP BY ?title \n" +
				"ORDER BY DESC(?nfestivals)";
		
		QueryExecutor queryExecutor = new QueryExecutor();
		
		ResultSet queryResults = queryExecutor.executeSelectSparqlQuery(
						query.toString(),
						DataModelManager.getInstance().getModel());
		
		HashMap<String, Integer> genresMap = new HashMap<String, Integer>();
		
		while (queryResults.hasNext()) {
			QuerySolution solution = (QuerySolution) queryResults.next();
			
			String title = ((Literal) solution.get("title")).getLexicalForm();
			int nFestivals = ((Literal) solution.get("nfestivals")).getInt();
			
			genresMap.put(title, nFestivals);
		}
		HashMap<String, Integer> genresMapSorted = Util.sortMap(genresMap);
		
		
		return genresMapSorted;
	}

	@Override
	public Genre getGenre(String uri) {
		return (Genre) DataModelManager.getInstance().load(uri);
	}

}