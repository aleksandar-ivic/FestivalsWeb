package rs.fon.is.festivals.services;

import java.util.ArrayList;
import java.util.Collection;

import rs.fon.is.festivals.domain.Festival;
import rs.fon.is.festivals.domain.Genre;
import rs.fon.is.festivals.persistence.DataModelManager;
import rs.fon.is.festivals.util.Constants;

public class FestivalServiceImpl implements FestivalService {

	QueryExecutor queryExecutor;

	public FestivalServiceImpl() {
		queryExecutor = new QueryExecutor();
	}

	@Override
	public Collection<Festival> getFestivals(String genre, String dateFrom, String dateTo) {
		StringBuffer query = new StringBuffer();
		// prefix part
		query.append("PREFIX dc:<" + Constants.DC + ">");
		query.append("PREFIX mo:<" + Constants.MO + "> \n");
		query.append("PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n");
		query.append("PREFIX ns:<" + Constants.NS + "> \n");
		query.append("PREFIX event:<" + Constants.EVENT + "> \n");
		query.append("PREFIX tl:<" + Constants.TL + "> \n");
		query.append("PREFIX xsd:<" + Constants.XSD + "> \n");
		// select part
		query.append("SELECT DISTINCT ?festival \n");

		// where
		query.append("WHERE\n{\n");
		

		
		if (!genre.isEmpty() && dateFrom.isEmpty() && dateTo.isEmpty()) {
			
			String[] genres = genre.split(",");
			for (int i = 0; i < genres.length; i++) {
				query.append(" \t?festival rdf:type mo:Festival;\n");
				query.append("	\tmo:genre ?genre"+i+" .\n");
				query.append("\t?genre"+i+" a mo:Genre ;\n");
				query.append("	\tdc:title ?genreName"+i+" .\n");
				query.append("\tFILTER regex(?genreName"+i+",\"" + genres[i] + "\")\n");
			}
			query.append("}");
			System.out.println(query);
		}
		if (!dateFrom.isEmpty() && !dateTo.isEmpty() && genre.isEmpty()) {
			query.append(" \t?festival rdf:type mo:Festival;\n");
			query.append("\tevent:time ?interval .\n");
			query.append("\t?interval a tl:Interval ;\n");
			query.append("	\ttl:start ?start ;\n");
			query.append("	\ttl:end ?end .\n");
			String[] splitedDateFrom = dateFrom.split("/");
			String[] splitedDateTo = dateTo.split("/");
			String tdbDateFrom = splitedDateFrom[2]+"-"+splitedDateFrom[0]+"-"+splitedDateFrom[1]+"T22:00:00Z";
			String tdbDateTo = splitedDateTo[2]+"-"+splitedDateTo[0]+"-"+splitedDateTo[1]+"T22:00:00Z";
			query.append("\tFILTER(?start >= \"" + tdbDateFrom + "\"^^xsd:dateTime && ?end <= \"" + tdbDateTo + "\"^^xsd:dateTime)\n");
			query.append("}");
			System.out.println(query);
		}
		
		if (!dateFrom.isEmpty() && !dateTo.isEmpty() && !genre.isEmpty()) {
			query.append(" \t?festival rdf:type mo:Festival;\n");
			query.append("\tevent:time ?interval ;\n");
			query.append("	\ttl:start ?start ;\n");
			query.append("	\ttl:end ?end .\n");
			String[] genres = genre.split(",");
			for (int i = 0; i < genres.length; i++) {
				query.append(" \t?festival rdf:type mo:Festival;\n");
				query.append("	\tmo:genre ?genre"+i+" ;\n");
				query.append("\t?genre"+i+" a mo:Genre ;\n");
				query.append("	\tdc:title ?genreName"+i+" .\n");
				query.append("\tFILTER regex(?genreName"+i+",\"" + genres[i] + "\")\n");
			}
			String[] splitedDateFrom = dateFrom.split("/");
			String[] splitedDateTo = dateTo.split("/");
			String tdbDateFrom = splitedDateFrom[2]+"-"+splitedDateFrom[0]+"-"+splitedDateFrom[1]+"T22:00:00Z";
			String tdbDateTo = splitedDateTo[2]+"-"+splitedDateTo[0]+"-"+splitedDateTo[1]+"T22:00:00Z";
			query.append("\tFILTER(?start >= \"" + tdbDateFrom + "\"^^xsd:dateTime && ?end <= \"" + tdbDateTo + "\"^^xsd:dateTime)\n");		
			query.append("}");
			System.out.println(query);
		}

		
		QueryExecutor queryExecutor = new QueryExecutor();
		Collection<String> queryResults = queryExecutor
				.executeOneVariableSelectSparqlQuery(query.toString(),
						"festival", DataModelManager.getInstance().getModel());
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

	@Override
	public int getNumberOfFestivalsWithGenre(String genre) {
		StringBuffer query = new StringBuffer();
		// prefix part
		query.append("PREFIX dc:<" + Constants.DC + ">");
		query.append("PREFIX mo:<" + Constants.MO + "> \n");
		query.append("PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n");
		query.append("PREFIX ns:<" + Constants.NS + "> \n");
		// select part
		query.append("SELECT DISTINCT ?festival\n");

		// where
		query.append("WHERE\n{\n");
		query.append(" \t?festival rdf:type mo:Festival;\n");

		query.append("	\tmo:genre ?genre .\n");
		query.append("\t?genre a mo:Genre ;\n");
		query.append("	\tdc:title ?genreName .\n");
		query.append("\tFILTER regex(?genreName,\"" + genre + "\")\n");

		query.append("}");
		QueryExecutor queryExecutor = new QueryExecutor();
		Collection<String> queryResults = queryExecutor
				.executeOneVariableSelectSparqlQuery(query.toString(),
						"festival", DataModelManager.getInstance().getModel());
		Collection<Festival> festivals = new ArrayList<>();
		if (queryResults != null && !queryResults.isEmpty()) {
			return queryResults.size();

		}
		return -1;
	}

	@Override
	public Collection<Festival> getAllFestivals() {
		StringBuffer query = new StringBuffer();
		// prefix part
		query.append("PREFIX dc:<" + Constants.DC + ">");
		query.append("PREFIX mo:<" + Constants.MO + "> \n");
		query.append("PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n");
		query.append("PREFIX ns:<" + Constants.NS + "> \n");
		// select part
		query.append("SELECT DISTINCT ?festival \n");

		// where
		query.append("WHERE\n{\n");
		query.append(" \t?genre rdf:type mo:Festival.\n");
		query.append("}");
		QueryExecutor queryExecutor = new QueryExecutor();
		Collection<String> queryResults = queryExecutor
				.executeOneVariableSelectSparqlQuery(query.toString(),
						"festival", DataModelManager.getInstance().getModel());
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

}
