package rs.fon.is.festivals.services;

import java.util.Collection;

import rs.fon.is.festivals.domain.Festival;
import rs.fon.is.festivals.persistence.DataModelManager;

public class FestivalServiceImpl implements FestivalService {

	QueryExecutor queryExecutor;

	public FestivalServiceImpl() {
		queryExecutor = new QueryExecutor();
	}

	@Override
	public Collection<Festival> getFestivals(String genre) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Festival getFestival(String uri) {
		return (Festival) DataModelManager.getInstance().load(uri);
	}

}
