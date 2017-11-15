package services;

import interfaces.IEventValidationService;
import interfaces.ILocationAnalyser;
import models.*;
import org.springframework.stereotype.Service;
import repositories.EventRepository;

import java.util.Date;

/**
 * Service class to handle the the business logic validation of an event
 */
@Service
public class EventValidationService implements IEventValidationService {
    private final EventRepository eventRepository;
    private final CardReaderLocationService cardReaderLocationService;
    private final ILocationAnalyser locationAnalyser;

    EventValidationService(EventRepository eventRepository, CardReaderLocationService cardReaderLocationService, ILocationAnalyser locationAnalyser){
        this.eventRepository = eventRepository;
        this.cardReaderLocationService = cardReaderLocationService;
        this.locationAnalyser = locationAnalyser;
    }

    /**
     * Checks if a request is using a valid card from its card id and panel id.
     *
     * @param request The current request that just happened
     * @return
     */
    public EventValidationResponse validateRequest(EventValidationRequest request){
        // Create the time from this request
        long timestamp = new Date().getTime();

        // Call CardReaderLocationService to get the location from the request's card ID
        Location location = cardReaderLocationService.getLocationFromPanelID(request.getPanelId());

        // Get the most recent event for that event ID
        Event previousEvent = eventRepository.findFirstByCardId(request.getCardId());

        // Create the current Compare geolocation
        Event currentEvent = new Event(request.getPanelId(), request.getCardId(), request.getAccessAllowed(), timestamp, location);

        // Store event in database so it is available for the next request
        eventRepository.save(currentEvent);

        // Analyse the events to see if its humanly possible for somebody to travel that distance in the time period
        LocationAnalysisResult locationAnalysis = locationAnalyser.checkIsHumanlyPossible(currentEvent, previousEvent);

        // Return EventValidationResponse from the location analysis
        return new EventValidationResponse(locationAnalysis, currentEvent, previousEvent);
    }
}
