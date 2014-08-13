package rs.fon.is.festivals.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import rs.fon.is.festivals.util.Constants;
import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;

@Namespace(Constants.MO)
@RdfType("Festival")
public class Festival extends Thing {

	@RdfProperty(Constants.FOAF + "name")
	private String festivalName;

	@RdfProperty(Constants.EVENT + "time")
	private Interval interval;

	@RdfProperty(Constants.MO + "MusicArtist")
	private Collection<MusicArtist> lineup;

	@RdfProperty(Constants.EVENT + "place")
	private Location location;
	
	@RdfProperty(Constants.MO + "genre")
	private Collection<Genre> genres;

	public Festival() {
	}

	public Festival(String festivalName, Interval interval, Location location) {
		this.festivalName = festivalName;
		this.interval = interval;
		this.location = location;
		this.lineup = new ArrayList<>();
		this.genres = new ArrayList<>();
	}

	public String getFestivalName() {
		return festivalName;
	}

	public void setFestivalName(String festivalName) {
		this.festivalName = festivalName;
	}

	public Interval getInterval() {
		return interval;
	}

	public void setInterval(Interval interval) {
		this.interval = interval;
	}

	public Collection<MusicArtist> getLineup() {
		return lineup;
	}

	public void setLineup(Collection<MusicArtist> lineup) {
		this.lineup = lineup;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	public Collection<Genre> getGenres(){
		return genres;
	}
	
	public void setGenres(Collection<Genre> genres){
		this.genres = genres;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return festivalName + " Datum: " + interval.toString() + " lineup"
				+ lineup.toString();
	}

}
