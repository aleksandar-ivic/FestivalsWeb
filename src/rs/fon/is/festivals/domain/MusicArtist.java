package rs.fon.is.festivals.domain;

import java.util.ArrayList;
import java.util.Collection;

import rs.fon.is.festivals.util.Constants;
import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;

@Namespace(Constants.MO)
@RdfType("MusicArtist")
public class MusicArtist extends Thing {

	@RdfProperty(Constants.FOAF + "name")
	private String name;

	@RdfProperty(Constants.MO + "genre")
	private Collection<String> genres;

	public MusicArtist() {

	}

	public MusicArtist(String name) {
		this.name = name;
		this.genres = new ArrayList<String>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<String> getGenres() {
		return genres;
	}

	public void setGenres(Collection<String> genres) {
		this.genres = genres;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name + " zanrovi:" + genres.toString();
	}

}
