package interfaces;

import models.EventValidationRequest;
import models.EventValidationResponse;

/**
 * Interface for the resource that will implement the core business logic that checks if card swipe Event is valid from a given request
 *
 */
public interface IEventValidationService {
    public EventValidationResponse validateRequest(EventValidationRequest request);
}
