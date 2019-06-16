package it.polito.tdp.model;

public class EventLatLong {

	private Long incident_id;
	private double geo_lon;
	private double geo_lat;
	private Integer district_id;

	public EventLatLong(Long incident_id, double geo_lon, double geo_lat, Integer district_id) {
		this.incident_id = incident_id;
		this.geo_lon = geo_lon;
		this.geo_lat = geo_lat;
		this.district_id = district_id;
	}

	public Long getIncident_id() {
		return incident_id;
	}

	public double getGeo_lon() {
		return geo_lon;
	}

	public double getGeo_lat() {
		return geo_lat;
	}

	public Integer getDistrict_id() {
		return district_id;
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
		EventLatLong other = (EventLatLong) obj;
		if (district_id == null) {
			if (other.district_id != null)
				return false;
		} else if (!district_id.equals(other.district_id))
			return false;
		return true;
	}

}
