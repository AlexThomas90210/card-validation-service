package teame.services;

import teame.models.Location;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Service Class abstraction for the external Card Reader service. Implements its own http client configure to call the API of the Card Reader Service
 *
 */
@Service
public class CardReaderLocationService implements ICardReaderLocationService {

    public CardReaderLocationService(){

    }

    public Location getLocationFromPanelID(String panelId) {
        try {
            String rawJson = getDataFromExternalCardReaderService(panelId);
            return mapJsonToLocation(rawJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getDataFromExternalCardReaderService(String panelId) throws Exception{
        URL url = new URL("http://uuidlocator.cfapps.io/api/panels/" + panelId);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        Scanner scanner = new Scanner(url.openStream());
        String data = "";
        while (scanner.hasNext()){
            data += scanner.nextLine();
        }
        scanner.close();
        return data;
    }

    private Location mapJsonToLocation(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Location location = objectMapper.readValue(json, Location.class);
        return location;
    }
}
