package it.polito.tdp.model;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

public class District {

	private Integer district_id;
	private double geo_lon;
	private double geo_lat;
	private LatLng coords;

	public District(Integer district_id, double geo_lon, double geo_lat) {
		this.district_id = district_id;
		this.geo_lon = geo_lon;
		this.geo_lat = geo_lat;
	}

	public Integer getDistrict_id() {
		return district_id;
	}

	public double getGeo_lon() {
		return geo_lon;
	}

	public double getGeo_lat() {
		return geo_lat;
	}

	public LatLng getCoords() {
		return coords;
	}

	public void setCoords() {
		this.coords = new LatLng(this.geo_lat, this.geo_lon);
	}

	public void setDistrict_id(Integer district_id) {
		this.district_id = district_id;
	}

	public void setGeo_lon(double geo_lon) {
		this.geo_lon = geo_lon;
	}

	public void setGeo_lat(double geo_lat) {
		this.geo_lat = geo_lat;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((district_id == null) ? 0 : district_id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		District other = (District) obj;
		if (district_id == null) {
			if (other.district_id != null)
				return false;
		} else if (!district_id.equals(other.district_id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[" + this.district_id + "]";
	}

	public double distanzada(District d) {
		return LatLngTool.distance(this.coords, d.getCoords(), LengthUnit.KILOMETER);
	}

}
