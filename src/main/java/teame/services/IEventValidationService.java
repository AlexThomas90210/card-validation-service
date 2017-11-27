package teame.services;

import teame.models.EventValidationRequest;
import teame.models.EventValidationResponse;

/**
 * Interface for the resource that will implement the core business logic that checks if card swipe Event.java is valid from a given request
 *
 */
public interface IEventValidationService {
    EventValidationResponse validateRequest(EventValidationRequest request);
}
