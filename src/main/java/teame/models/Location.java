package teame.models;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.util.HashMap;
import java.util.Map;

/**
 * Data structure for the associated location data of a card reader
 */
@Embeddable
public class Location {
    @JsonProperty("coordinates")
    private Coordinates coordinates;
    @JsonProperty("altitude")
    private Double altitude;
    @JsonProperty("relativeLocation")
    private String relativeLocation;
    @JsonIgnore
    @Transient
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("coordinates")
    public Coordinates getCoordinates() {
        return coordinates;
    }

    @JsonProperty("coordinates")
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @JsonProperty("altitude")
    public Double getAltitude() {
        return altitude;
    }

    @JsonProperty("altitude")
    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    @JsonProperty("relativeLocation")
    public String getRelativeLocation() {
        return relativeLocation;
    }

    @JsonProperty("relativeLocation")
    public void setRelativeLocation(String relativeLocation) {
        this.relativeLocation = relativeLocation;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Location(){

    }

    public Location(Coordinates coordinates, Double altitude, String relativeLocation) {
        this.coordinates = coordinates;
        this.altitude = altitude;
        this.relativeLocation = relativeLocation;
    }
}
