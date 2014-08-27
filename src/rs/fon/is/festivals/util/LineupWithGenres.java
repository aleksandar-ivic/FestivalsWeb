package rs.fon.is.festivals.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;

import rs.fon.is.festivals.domain.Genre;
import rs.fon.is.festivals.domain.MusicArtist;

public class LineupWithGenres {
	
	private Collection<MusicArtist> artists;
	private Collection<Genre> genres;
	
	public LineupWithGenres(){
		artists = new ArrayList<>();
		genres = new LinkedHashSet<>();
	}

	public Collection<MusicArtist> getArtists() {
		return artists;
	}

	public void setArtists(Collection<MusicArtist> artists) {
		this.artists = artists;
	}

	public Collection<Genre> getGenres() {
		return genres;
	}

	public void setGenres(Collection<Genre> genres) {
		this.genres = genres;
	}
	
	

}
