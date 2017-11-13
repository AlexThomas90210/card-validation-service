package models;

/**
 *  Data structure for longitude and latitude coordinates
 */
public class Coordinates {
    private Float longitude;
    private Float latitude;

    public Coordinates(Float longitude, Float latitude){
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }
}
