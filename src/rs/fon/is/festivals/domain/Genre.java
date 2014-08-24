package rs.fon.is.festivals.domain;

import java.io.Serializable;

import rs.fon.is.festivals.util.Constants;
import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;

@Namespace(Constants.MO)
@RdfType("Genre")
public class Genre extends Thing implements Serializable {

	/**
	 * 
	 */
	private static long serialVersionUID = 1L;

	@RdfProperty(Constants.DC + "title")
	private String title;

	public Genre() {

	}

	public Genre(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return title;
	}

}
