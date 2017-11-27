package teame.services;

import teame.models.Event;
import teame.models.LocationAnalysisResult;

/**
 * Interface for the resource that will analyse 2 given locations and see if its humanly possible to travel within that time period
 *
 */
public interface ILocationAnalyser {
    LocationAnalysisResult checkIsHumanlyPossible(Event currentEvent, Event previousEvent);
}
