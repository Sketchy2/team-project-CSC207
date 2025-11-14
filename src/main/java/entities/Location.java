package entities;

import java.util.Objects;

public class Location {
    private final String name;
    private final String countryCode;
    private final Double latitude;
    private final Double longitude;

    public Location(String name, String countryCode, Double latitude, Double longitude) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("City name cannot be empty");
        }
        if (Double.isNaN(latitude) || latitude > 90 || latitude < -90) {
            throw new IllegalArgumentException("Latitude must be between -90 and 90");
        }
        if (Double.isNaN(longitude) || longitude > 180 || longitude < -180) {
            throw new IllegalArgumentException("Longitude must be between -180 and 180");
        }
        this.name = name.trim();
        this.countryCode = countryCode;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }
    public String getCountryCode() {
        return countryCode;
    }
    public Double getLatitude() {
        return latitude;
    }
    public Double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return String.format("%s (%s): [%.4f, %.4f]", name, countryCode, latitude, longitude);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location)) return false;
        Location that = (Location) o;
        return Double.compare(that.latitude, latitude) == 0 &&
                Double.compare(that.longitude, longitude) == 0 &&
                name.equals(that.name) &&
                countryCode.equals(that.countryCode);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(name, countryCode, latitude, longitude);
    }
}
