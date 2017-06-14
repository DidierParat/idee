package idee;

import idee.Clients.AdaptiveClient;
import idee.Clients.DntClient;
import idee.Clients.LocationForecastClient;
import idee.Nasjonalturbase.Area;
import idee.Nasjonalturbase.Trip;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Created by didier on 20.01.17.
 */
public final class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(final String[] args) {
        Config config = new Config(args);

        // User preferences
        final Location userLocation = new Location(10.729174, 59.916649);
        final Integer searchRadiusKm = 20;
        final Forecast.Weather userWeatherPreference = Forecast.Weather.Cloudy;

        // Initialize
        final AdaptiveClient adaptiveClient = new AdaptiveClient();
        final LocationForecastClient locationForecastClient
                = new LocationForecastClient(adaptiveClient);
        final DntClient dntClient = new DntClient(config.dntHost, config.dntApiKey, adaptiveClient);
        final Calendar saturday = getNextSaturday();
        final MapOfNorway mapOfNorway = new MapOfNorway(dntClient);

        // Get areas nearby
        Set<Area> areasNearby = mapOfNorway.getNearbyAreas(userLocation, searchRadiusKm);
        if (areasNearby.isEmpty()) {
            LOGGER.info("No area found nearby your location. "
                    + "Either increase the search radius or consider relocating.");
            return;
        }

        // Get weather of nearby cities and filter cities on weather
        Iterator<Area> areasNearbyIte = areasNearby.iterator();
        Set<Forecast> forecastNearbyAreas = new HashSet<>();
        while (areasNearbyIte.hasNext()) {
            Area area = areasNearbyIte.next();
            try {
                Forecast forecast = locationForecastClient.getWeather(area, saturday);
                if (forecast.getWeather().equals(userWeatherPreference)) {
                    forecastNearbyAreas.add(forecast);
                }
            } catch (Exception e) {
                LOGGER.warning(
                        "Could not get forecast for area "
                        + area.getId()
                        + ". Exception: "
                        + e);
                return;
            }
        }
        if (forecastNearbyAreas.isEmpty()) {
            LOGGER.severe("No area with weather forecast corresponding to user's preferences.");
            return;
        }

        // Get suggested trips in these areas
        Iterator<Forecast> forecastNearbyAreasIte = forecastNearbyAreas.iterator();
        Set<Trip> trips = new HashSet<>();
        while(forecastNearbyAreasIte.hasNext()) {
            Area area = forecastNearbyAreasIte.next().getArea();
            Trip trip;
            final String areaId = area.getId();
            try {
                trip = dntClient.getTripPerArea(areaId);
            } catch (Exception e) {
                LOGGER.warning("Could not get trips for area " + areaId + ". Exception: " + e);
                continue;
            }
            trips.add(trip);
        }
        if (trips.isEmpty()) {
            LOGGER.severe("No trips found.");
            return;
        }

        LOGGER.info("I found " + trips.size() + " trips that could interest you!");
        for(Iterator tripsIte = trips.iterator(); tripsIte.hasNext();) {
            LOGGER.info(tripsIte.next().toString());
        }
    }

    private static Calendar getNextSaturday() {
        final Calendar nextSaturday = Calendar.getInstance();
        int currentDay = nextSaturday.get(Calendar.DAY_OF_WEEK);
        int daysBeforeSaturday = Calendar.SATURDAY - currentDay;
        nextSaturday.add(Calendar.DAY_OF_WEEK, daysBeforeSaturday);
        return nextSaturday;
    }

    private Main() {
        //not called
    }
}
