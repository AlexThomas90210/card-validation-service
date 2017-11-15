package services;

import interfaces.ILocationAnalyser;
import models.Event;
import models.LocationAnalysisResult;
import org.springframework.stereotype.Service;

@Service
public class GoogleMapsLocationAnalyser implements ILocationAnalyser {

    @Override
    public LocationAnalysisResult checkIsHumanlyPossible(Event currentEvent, Event previousEvent) {
        // TODO: Implement logic to call Google Maps Matrix API and check if the events are valid or not
        return null;
    }
}
