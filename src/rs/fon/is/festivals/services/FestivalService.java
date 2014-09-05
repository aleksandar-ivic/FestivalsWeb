package rs.fon.is.festivals.services;

import java.util.Collection;

import rs.fon.is.festivals.domain.Festival;

public interface FestivalService {
	
	public Collection<Festival> getFestivals(String genre, String dateFrom, String dateTo);
	
	public Collection<Festival> getAllFestivals();
	
	public Festival getFestival(String uri);

	//public int getNumberOfFestivalsWithGenre(String genre);
}
