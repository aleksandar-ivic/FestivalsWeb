package rs.fon.is.festivals.services;

import java.util.ArrayList;
import java.util.Collection;

import rs.fon.is.festivals.domain.Festival;
import rs.fon.is.festivals.persistence.DataModelManager;
import rs.fon.is.festivals.util.Constants;

public class FestivalServiceImpl implements FestivalService {

	private QueryExecutor queryExecutor;

	public FestivalServiceImpl() {
		queryExecutor = new QueryExecutor();
	}

	@Override
	public Collection<Festival> getFestivals(String genre, String dateFrom,
			String dateTo) {
		StringBuffer query = new StringBuffer();

		query.append("PREFIX dc:<" + Constants.DC + "> \n" + "PREFIX mo:<"
				+ Constants.MO + "> \n" + "PREFIX rdf:<" + Constants.RDF
				+ ">\n" + "PREFIX event:<" + Constants.EVENT + "> \n"
				+ "PREFIX tl:<" + Constants.TL + "> \n" + "PREFIX xsd:<"
				+ Constants.XSD + "> \n" +

				"SELECT DISTINCT ?festival \n" + "WHERE { \n");

		if (!genre.equals("")) {
			String[] genres = genre.split(",");
			for (int i = 0; i < genres.length; i++) {
				query.append(" \t?festival rdf:type mo:Festival ;\n");
				query.append("	\tmo:genre ?genre" + i + " .\n");
				query.append("\t?genre" + i + " a mo:Genre ;\n");
				query.append("	\tdc:title ?genreName" + i + " .\n");
				query.append("\tFILTER regex(?genreName" + i + ",\""
						+ genres[i] + "\")\n");
			}
			query.append("}");
			System.out.println(query);
		}

		if (!dateFrom.equals("") && !dateTo.equals("")) {
			String[] splitedDateFrom = dateFrom.split("/");
			String[] splitedDateTo = dateTo.split("/");

			String tdbDateFrom = splitedDateFrom[2] + "-" + splitedDateFrom[0]
					+ "-" + splitedDateFrom[1] + "T22:00:00Z";
			String tdbDateTo = splitedDateTo[2] + "-" + splitedDateTo[0] + "-"
					+ splitedDateTo[1] + "T22:00:00Z";

			query.append("?festival rdf:type mo:Festival ; \n"
					+ "\t event:time ?interval . \n" +

					"?interval a tl:Interval ; \n" + "\t tl:start ?start ; \n"
					+ "\t tl:end ?end .\n" +

					"FILTER(?start >= \"" + tdbDateFrom
					+ "\"^^xsd:dateTime && ?end <= \"" + tdbDateTo
					+ "\"^^xsd:dateTime) \n" + "}");
			System.out.println(query);
		}

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
	public Collection<Festival> getAllFestivals() {
		StringBuffer query = new StringBuffer();
		// prefix part
		query.append("PREFIX mo:<" + Constants.MO + "> \n" + "PREFIX rdf:<"
				+ Constants.RDF + "> \n" +

				"SELECT DISTINCT ?festival \n" +

				"WHERE {\n" + "\t ?genre rdf:type mo:Festival . \n" + "}");

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