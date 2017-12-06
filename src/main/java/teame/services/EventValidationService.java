package teame.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import teame.models.*;
import org.springframework.stereotype.Service;
import teame.repositories.EventRepository;

import java.util.Date;

/**
 * Service class to handle the the business logic validation of an event
 */
@Service
public class EventValidationService implements IEventValidationService {
    private final EventRepository eventRepository;
    private final ICardReaderLocationService cardReaderLocationService;
    private final ILocationAnalyser locationAnalyser;
    private final INotificationService notificationService;

    EventValidationService(EventRepository eventRepository, CardReaderLocationService cardReaderLocationService, ILocationAnalyser locationAnalyser, INotificationService notificationService){
        this.eventRepository = eventRepository;
        this.cardReaderLocationService = cardReaderLocationService;
        this.locationAnalyser = locationAnalyser;
        notificationService.initialize("tcp://localhost:1883");
        this.notificationService = notificationService;
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
        Event previousEvent = eventRepository.findFirstByCardIdOrderByTimestampDesc(request.getCardId());

        // Create the current Compare geolocation
        Event currentEvent = new Event(request.getPanelId(), request.getCardId(), request.getAccessAllowed(), timestamp, location);

        // Store event in database so it is available for the next request
        eventRepository.save(currentEvent);

        // Analyse the events to see if its humanly possible for somebody to travel that distance in the time period and create a response from the data
        LocationAnalysisResult locationAnalysis = locationAnalyser.checkIsHumanlyPossible(currentEvent, previousEvent);
        EventValidationResponse response = new EventValidationResponse(locationAnalysis, currentEvent, previousEvent);

        // If the location analysis result says the event is not valid then send a notification using the notification service
        if (!locationAnalysis.getValid()){
            ObjectMapper mapper = new ObjectMapper();
            try {
                this.notificationService.publish("topic", mapper.writeValueAsString(response));
            } catch (Exception e) {
                System.out.println("Error mapping response to JSON for MQTT");
            }
        }

        // Return EventValidationResponse from the location analysis
        return response;
    }
}
