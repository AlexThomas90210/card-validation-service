package models;

/**
 * Response object from an event validation request
 */
public class EventValidationResponse {
    private Boolean validEvent;
    private String reason;
    private Event currentEvent;
    private Event previousEvent;

    public EventValidationResponse(Boolean validEvent, String reason, Event currentEvent, Event previousEvent) {
        this.validEvent = validEvent;
        this.reason = reason;
        this.currentEvent = currentEvent;
        this.previousEvent = previousEvent;
    }

    public EventValidationResponse(LocationAnalysisResult locationAnalysis, Event currentEvent, Event previousEvent) {
        this.validEvent = locationAnalysis.getValid();
        this.reason = locationAnalysis.getReason();
        this.currentEvent = currentEvent;
        this.previousEvent = previousEvent;
    }

    public Boolean getValidEvent() {
        return validEvent;
    }

    public void setValidEvent(Boolean validEvent) {
        this.validEvent = validEvent;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Event getCurrentEvent() {
        return currentEvent;
    }

    public void setCurrentEvent(Event currentEvent) {
        this.currentEvent = currentEvent;
    }

    public Event getPreviousEvent() {
        return previousEvent;
    }

    public void setPreviousEvent(Event previousEvent) {
        this.previousEvent = previousEvent;
    }
}
