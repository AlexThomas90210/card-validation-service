import org.junit.Test;
import teame.models.Location;
import teame.services.CardReaderLocationService;

import static org.junit.Assert.assertEquals;

public class CardReaderLocationServiceTest {
    @Test
    public void canGetPanelIdDataFromExternalService(){
        CardReaderLocationService service = new CardReaderLocationService();

        Location location = service.getLocationFromPanelID("580ddc98-0db9-473d-a721-348f353f1d2b");
        assertEquals((Double) 100.0, location.getAltitude());
        assertEquals((Double) 51.884827, location.getCoordinates().getLatitude());
        assertEquals((Double)(-8.533947), location.getCoordinates().getLongitude());
        assertEquals("CIT Library West Wing Entry Doors, Cork, Ireland", location.getRelativeLocation());
    }
}
