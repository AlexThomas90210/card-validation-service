package models;

import javax.persistence.Entity;

/**
 * Class for the events & associated information that the system handles
 */
@Entity
public class Event {
    private String panelId;
    private String cardId;
    private Boolean accessAllowed;
    private long timestamp;
    private Location location;

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
}
