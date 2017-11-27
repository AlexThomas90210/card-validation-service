package teame.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Class for the events & associated information that the system handles
 */
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @JsonIgnore
    private Long id;
    private String panelId;
    private String cardId;
    private Boolean accessAllowed;
    private long timestamp;
    @Embedded
    private Location location;

    protected Event() {

    }

    public Event(String panelId, String cardId, Boolean accessAllowed, long timestamp, Location location){
        this.panelId = panelId;
        this.cardId = cardId;
        this.accessAllowed = accessAllowed;
        this.timestamp = timestamp;
        this.location = location;
    }

    public String getPanelId() {
        return panelId;
    }

    public void setPanelId(String panelId) {
        this.panelId = panelId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Boolean getAccessAllowed() {
        return accessAllowed;
    }

    public void setAccessAllowed(Boolean accessAllowed) {
        this.accessAllowed = accessAllowed;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
