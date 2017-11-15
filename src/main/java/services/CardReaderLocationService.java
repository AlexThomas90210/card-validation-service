package services;

import interfaces.ICardReaderLocationService;
import models.Location;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Service Class abstraction for the external Card Reader service. Implements its own http client configure to call the API of the Card Reader Service
 *
 */
@Service
public class CardReaderLocationService implements ICardReaderLocationService {

    public CardReaderLocationService(){

    }

    public Location getLocationFromPanelID(String panelId) {
        // TODO: Implement a http client and call the REST service provided
        try {
            URL url = new URL("http://crunchify.com/");
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String strTemp = "";
            while (null != (strTemp = br.readLine())) {
                System.out.println(strTemp);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
