package teame.services;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.*;
import teame.models.Coordinates;
import teame.models.Event;
import teame.models.LocationAnalysisResult;
import org.springframework.stereotype.Service;

/**
   Google maps implementation of the ILocationAnalyser interface to see if two events are humanly possible to travel in the given timeframe
 An issue with the Google Maps API is that it does not support flight travel estimation when using the API unlike when using google maps
 Therefore, if the calculateDistance of 2 events is greater than the tresh hold, then this class simulates an estimate flight travel time between
 the two events by using the average plane speed to see if its humanly possible to travel the calculateDistance within the allocated time
 */
@Service
public class GoogleMapsLocationAnalyser implements ILocationAnalyser {
    private final String API_KEY = "AIzaSyDbAIn0TvxAGCZhO4DpdXPuXSwDWFjI7a4";
    private GeoApiContext context;
    private Integer simulateFlightTreshold = 500;

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
        LatLng originLatLng = new LatLng(originCoordinates.getLatitude(), originCoordinates.getLongitude());
        LatLng destinationLatLng = new LatLng(destinationCoordinates.getLatitude(), destinationCoordinates.getLongitude());

        double distance = calculateDistance(originLatLng, destinationLatLng, 'K');
        long timeDifferenceBetweenEvents = (currentEvent.getTimestamp() - previousEvent.getTimestamp());

        Duration duration = null;
        if ( distance > 0 && distance < simulateFlightTreshold) {
            // Make Google Matrix Request
            DistanceMatrix matrix = null;
            try {
                matrix = DistanceMatrixApi.newRequest(context)
                        .origins(originLatLng)
                        .destinations(destinationLatLng)
                        .await();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Get the minimum amount of seconds it could take to travel that calculateDistance
            long minimumSecondsAllowed = 999999999;
            for (DistanceMatrixRow row : matrix.rows){
                for (DistanceMatrixElement element : row.elements){
                    if (element.duration != null && element.duration.inSeconds < minimumSecondsAllowed){
                        minimumSecondsAllowed = element.duration.inSeconds;
                        duration = element.duration;
                    }
                }
            }
        }

        if (duration == null){
            // The duration will be null if Google maps cannot find a route between the locations without using a flight,
            // Therefore we simulate a flight for the distance calculated between the events
            duration = simulateFlightDuration(distance);
        }

        addDurationTimeForEventAltitude(currentEvent.getLocation().getAltitude(),previousEvent.getLocation().getAltitude(), duration);

        return generateAnalysis(duration, timeDifferenceBetweenEvents);
    }

    private LocationAnalysisResult generateAnalysis(Duration duration,long millisecondsBetweenEvents){
        String reasonState = "Minimum time to travel is roughly" + duration.humanReadable + ".  " + duration.inSeconds + " seconds exactly. The time difference between events is " + millisecondsBetweenEvents/1000 + " seconds";
        if (duration.inSeconds * 1000 < millisecondsBetweenEvents){
            // Valid
            return new LocationAnalysisResult(true, "It is humanly possible to travel that distance. " + reasonState);
        } else {
            // Invalid
            return new LocationAnalysisResult(false, "It is not humanly possible to travel that distance. " + reasonState);
        }
    }

    private Duration simulateFlightDuration(double distance){
        double timeInHours = distance/700;
        Duration duration = new Duration();
        duration.inSeconds = (long)timeInHours * 60 * 60;
        duration.humanReadable = Math.round(timeInHours) + " hours";
        return duration;
    }

    private void addDurationTimeForEventAltitude(Double altitude1, Double altitude2, Duration duration){
        Double delta = Math.abs(altitude1 - altitude2);
        long additionalSeconds = (long) (delta * 2);
        duration.inSeconds += additionalSeconds;
    }

    //    Code to calculate calculateDistance from 2 LatLngs from stack overflow: https://stackoverflow.com/questions/3694380/calculating-distance-between-two-points-using-latitude-longitude-what-am-i-doi
    private double calculateDistance(LatLng origin, LatLng destination, char unit) {
        double theta = origin.lng - destination.lng;
        double dist = Math.sin(deg2rad(origin.lat)) * Math.sin(deg2rad(destination.lat)) + Math.cos(deg2rad(origin.lat)) * Math.cos(deg2rad(destination.lat)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == 'K') {
            dist = dist * 1.609344;
        } else if (unit == 'N') {
            dist = dist * 0.8684;
        }
        return (dist);
    }


    //  This function converts decimal degrees to radians
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    //  This function converts radians to decimal degrees
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
