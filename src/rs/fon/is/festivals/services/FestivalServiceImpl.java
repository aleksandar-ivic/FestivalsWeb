package rs.fon.is.festivals.services;

import java.util.ArrayList;
import java.util.Collection;

import rs.fon.is.festivals.domain.Festival;
import rs.fon.is.festivals.persistence.DataModelManager;
import rs.fon.is.festivals.util.Constants;

public class FestivalServiceImpl implements FestivalService {

	QueryExecutor queryExecutor;

	public FestivalServiceImpl() {
		queryExecutor = new QueryExecutor();
	}

	@Override
	public Collection<Festival> getFestivals(String genre) {
		StringBuffer query = new StringBuffer();
		// prefix part
		//query.append("PREFIX dc:<" + Constants.DC + ">");
		query.append("PREFIX mo:<" + Constants.MO + "> \n");
		query.append("PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n");
	    query.append("PREFIX ns:<" + Constants.NS + "> \n");
		// select part
		query.append("SELECT ?festival \n");

		// where
		query.append("WHERE\n{\n");
		query.append(" \t?festival rdf:type mo:Festival.\n");
		
		 //query.append("	mo:genre ?genre .");
		 //query.append("?genre a mo:Genre ;");
		 //query.append("	dc:title ?genreName .");
		 //query.append("FILTER regex(?genreName,\"" + genre + "\")");
		 
		query.append("}");
		QueryExecutor queryExecutor = new QueryExecutor();
		Collection<String> queryResults = queryExecutor
				.executeOneVariableSelectSparqlQuery(query.toString(),"festival", DataModelManager.getInstance().getModel());
		Collection<Festival> festivals = new ArrayList<>();
		if (queryResults != null) {// && !queryResults.isEmpty()) {
			for (String uri : queryResults) {
				Festival f = getFestival(uri);
				festivals.add(f);
			}
			return festivals;
		}
		return null;

	}

	@Override
	public Festival getFestival(String uri) {
		return (Festival) DataModelManager.getInstance().load(uri);
	}

}
