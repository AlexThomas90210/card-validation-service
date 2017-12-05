import org.junit.Test;

import teame.models.Coordinates;
import teame.models.Event;
import teame.models.Location;
import teame.models.LocationAnalysisResult;
import teame.services.GoogleMapsLocationAnalyser;

import static org.junit.Assert.assertEquals;

public class GoogleMapsLocationAnalyserTest {
    @Test
    public void isHumanlyPossible(){
        GoogleMapsLocationAnalyser locationAnalyser = new GoogleMapsLocationAnalyser();

        Location firstLocation = new Location(new Coordinates(-8.533947,51.884827), 5.0, "Some Location");
        Location secondLocation = new Location(new Coordinates( -0.133873,51.524885), 5.0, "Another Location");
        Event currentEvent  = new Event("123-123-123", "123-123-123" , true, 1511627249000L, firstLocation);
        Event previousEvent = new Event("123-123-123", "123-123-123" , true, 1511195249000L, secondLocation);

        LocationAnalysisResult locationAnalsysResult = locationAnalyser.checkIsHumanlyPossible(currentEvent, previousEvent);
        assertEquals(true, locationAnalsysResult.getValid());
    }

    @Test
    public void isNotHumanlyPossible(){
        GoogleMapsLocationAnalyser locationAnalyser = new GoogleMapsLocationAnalyser();

        Location firstLocation = new Location(new Coordinates(-8.533947,51.884827), 5.0, "Some Location");
        Location secondLocation = new Location(new Coordinates( -122.160999,37.428348), 5.0, "Another Location");
        Event currentEvent  = new Event("123-123-123", "123-123-123" , true, 1511627249000L, firstLocation);
        Event previousEvent = new Event("123-123-123", "123-123-123" , true, 1511627219000L, secondLocation);

        LocationAnalysisResult locationAnalsysResult = locationAnalyser.checkIsHumanlyPossible(currentEvent, previousEvent);
        assertEquals(false, locationAnalsysResult.getValid());
    }
}
