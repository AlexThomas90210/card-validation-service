package teame.services;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.*;
import teame.models.Coordinates;
import teame.models.Event;
import teame.models.LocationAnalysisResult;
import org.springframework.stereotype.Service;

@Service
public class GoogleMapsLocationAnalyser implements ILocationAnalyser {
    private final String API_KEY = "AIzaSyDbAIn0TvxAGCZhO4DpdXPuXSwDWFjI7a4";
    private GeoApiContext context;

    public GoogleMapsLocationAnalyser(){
        this.context = new GeoApiContext.Builder().apiKey(this.API_KEY).build();
    }

    @Override
    public LocationAnalysisResult checkIsHumanlyPossible(Event currentEvent, Event previousEvent) {
        if (previousEvent == null) {
            return new LocationAnalysisResult(true, "There is no previous event");
        }

        Coordinates originCoordinates = previousEvent.getLocation().getCoordinates();
        Coordinates destinationCoordinates = currentEvent.getLocation().getCoordinates();
        try {
            // Make Google Matrix Request
            DistanceMatrix matrix = DistanceMatrixApi.newRequest(context)
                    .origins(new LatLng(originCoordinates.getLatitude(), originCoordinates.getLongitude()))
                    .destinations(new LatLng(destinationCoordinates.getLatitude(), destinationCoordinates.getLongitude()))
                    .await();

            // Get the minimum amount of seconds it could take to travel that distance
            long minimumSecondsAllowed = 999999999;
            Duration duration = null;
            for (DistanceMatrixRow row : matrix.rows){
                for (DistanceMatrixElement element : row.elements){
                    if (element.duration != null && element.duration.inSeconds < minimumSecondsAllowed){
                        minimumSecondsAllowed = element.duration.inSeconds;
                        duration = element.duration;
                    }
                }
            }

            // Get the difference in seconds between the two events and compare that with the minimum seconds allowed
            long timeDifferenceBetweenEvents = (currentEvent.getTimestamp() - previousEvent.getTimestamp());
            return generateAnalysis(duration, timeDifferenceBetweenEvents);
        } catch (Exception e){
            System.out.println("Error:" + e.getMessage());
            System.out.println(e);
        }

        return null;
    }

    private LocationAnalysisResult generateAnalysis(Duration duration,long millisecondsBetweenEvents){
        String reasonState = "Minimum time to travel is " + duration.humanReadable + " or " + duration.inSeconds + " seconds. The time difference between events is " + millisecondsBetweenEvents/1000 + " seconds";
        if (duration.inSeconds * 1000 < millisecondsBetweenEvents){
            // Valid
            return new LocationAnalysisResult(true, "It is humanly possible to travel that distance. " + reasonState);
        } else {
            // Invalid
            return new LocationAnalysisResult(false, "Not humanly possible to travel that distance. " + reasonState);
        }
    }
}
