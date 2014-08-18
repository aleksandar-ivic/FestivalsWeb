package rs.fon.is.festivals.domain;

import rs.fon.is.festivals.util.Constants;
import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;

@Namespace(Constants.GEO)
@RdfType("SpatialThing")
public class Location extends Thing {

	@RdfProperty(Constants.DC + "title")
	private String locationName;

	@RdfProperty(Constants.GEO + "lat")
	private double lat;

	@RdfProperty(Constants.GEO + "long")
	private double lng;

	public Location() {

	}

	public Location(String locationName, double lat, double lng) {
		this.locationName = locationName;
		this.lat = lat;
		this.lng = lng;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

}
