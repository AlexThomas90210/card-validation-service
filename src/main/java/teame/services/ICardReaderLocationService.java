package teame.services;

import teame.models.Location;

/**
 * Interface for the resource that will get the location data from a given panel ID
 *
 */
public interface ICardReaderLocationService {
    Location getLocationFromPanelID(String panelId);
}
