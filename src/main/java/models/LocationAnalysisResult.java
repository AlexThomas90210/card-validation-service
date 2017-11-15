package models;

/**
 *
 */
public class LocationAnalysisResult {
    private Boolean isValid;
    private String reason;

    public LocationAnalysisResult(Boolean isValid, String reason) {
        this.isValid = isValid;
        this.reason = reason;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
