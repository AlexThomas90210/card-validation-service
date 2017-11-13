package models;

/**
 * Data structure for the associated location data of a card reader
 */
public class Location {
    private Coordinates coordinates;
    private Integer altitude;
    private String relativeLocation;

    public Location(Coordinates coordinates, Integer altitude, String relativeLocation) {
        this.coordinates = coordinates;
        this.altitude = altitude;
        this.relativeLocation = relativeLocation;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Integer getAltitude() {
        return altitude;
    }

    public void setAltitude(Integer altitude) {
        this.altitude = altitude;
    }

    public String getRelativeLocation() {
        return relativeLocation;
    }

    public void setRelativeLocation(String relativeLocation) {
        this.relativeLocation = relativeLocation;
    }
}
