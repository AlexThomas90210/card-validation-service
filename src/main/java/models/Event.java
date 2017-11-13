package models;

/**
 * Class for the events & associated information that the system handles
 */
public class Event {
    private String panelId = null;
    private String cardId = null;
    private Boolean accessAllowed = null;
    private Integer timestamp = null;
    private Location location = null;

    public Event(String panelId, String cardId, Boolean accessAllowed, Integer timestamp, Location location){
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

    public Integer getTimestamp() {
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
