package models;

/**
 * Class representing the put Event request
 */
public class EventValidationRequest {
    private Boolean accessAllowed;
    private String cardId;
    private String panelId;

    public EventValidationRequest(Boolean accessAllowed, String cardId, String panelId) {
        this.accessAllowed = accessAllowed;
        this.cardId = cardId;
        this.panelId = panelId;
    }

    public Boolean getAccessAllowed() {
        return accessAllowed;
    }

    public void setAccessAllowed(Boolean accessAllowed) {
        this.accessAllowed = accessAllowed;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getPanelId() {
        return panelId;
    }

    public void setPanelId(String panelId) {
        this.panelId = panelId;
    }
}
