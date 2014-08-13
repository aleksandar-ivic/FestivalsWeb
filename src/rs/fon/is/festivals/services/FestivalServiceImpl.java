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
		//prefix part
		query.append("PREFIX foaf:<" + Constants.FOAF + ">");
		query.append("PREFIX dc:<" + Constants.DC + ">");
		query.append("PREFIX mo:<" + Constants.MO + ">");
	    query.append("PREFIX ns:<" + Constants.NS + ">");
		//select part
		query.append("SELECT ?artist");
		
		//where
		query.append("WHERE { ?artist a mo:MusicArtist;");
		query.append("?genre foaf:name mo:Genre	.");
		query.append("FILTER regex(?genre,\"" + genre + "\")");
		query.append("}");
		Collection<String> queryResults =  queryExecutor.executeOneVariableSelectSparqlQuery(query.toString(), "festival", DataModelManager.getInstance().getModel());
		Collection<Festival> festivals = new ArrayList<>();
		if (queryResults != null && !queryResults.isEmpty()) {
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
