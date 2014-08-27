package rs.fon.is.festivals.domain;

import java.io.Serializable;
import java.net.URI;

import thewebsemantic.Id;


public class Thing implements Serializable{

	@Id
	private URI uri;
	
	public URI getUri() {
		return uri;
	}

	public void setUri(URI uri) {
		this.uri = uri;
	}
}
