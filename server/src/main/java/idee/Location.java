package idee;

import java.io.Serializable;

public class Location implements Serializable {
    private final Double longitude;
    private final Double latitude;

    public String toString() {
        return "Longitude: " + longitude + ". Latitude: " + latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Location(final Double longitude, final Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
