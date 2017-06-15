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
import org.apache.commons.cli.*;

/**
 * Created by didier on 20.01.17.
 */
public final class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(final String[] args) {
        Config config;
        try {
            config = new Config(args);
        } catch (ParseException e) {
            LOGGER.severe("Could not parse args." + e);
            return;
        }

        // User preferences
        final Location userLocation = new Location(10.729174, 59.916649);
        final Integer searchRadiusKm = 20;
        final Forecast.Weather userWeatherPreference = Forecast.Weather.Sunny;

        // Initialize
        final AdaptiveClient adaptiveClient = new AdaptiveClient();
        final LocationForecastClient locationForecastClient
                = new LocationForecastClient(adaptiveClient);
        final DntClient dntClient = new DntClient(config.dntHost, config.dntApiKey, adaptiveClient);
        final Calendar saturday = getNextSaturday();
        final MapOfNorway mapOfNorway = new MapOfNorway(dntClient);

        // Get areas nearby
        final Set<Area> areasNearby = mapOfNorway.getNearbyAreas(userLocation, searchRadiusKm);
        if (areasNearby.isEmpty()) {
            LOGGER.info("No area found nearby your location. "
                    + "Either increase the search radius or consider relocating.");
            return;
        }

        // Get weather of nearby cities and filter cities on weather
        final Set<Forecast> forecastNearbyAreas = new HashSet<>();
        for (final Area area:areasNearby) {
            try {
                final Forecast forecast = locationForecastClient.getWeather(area, saturday);
                if (forecast.getWeather().ordinal() <= userWeatherPreference.ordinal()) {
                    forecastNearbyAreas.add(forecast);
                }
            } catch (Exception e) {
                LOGGER.severe(
                        "Could not get forecast for area "
                        + area.getId()
                        + ". Exception: "
                        + e);
                return;
            }
        }
        if (forecastNearbyAreas.isEmpty()) {
            LOGGER.info("No area with weather forecast corresponding to user's preferences.");
            return;
        }

        // Get suggested trips in these areas
        final Set<Trip> trips = new HashSet<>();
        for(final Forecast forecast:forecastNearbyAreas) {
            final Area area = forecast.getArea();
            final Trip trip;
            final String areaId = area.getId();
            try {
                trip = dntClient.getTripPerArea(areaId);
            } catch (Exception e) {
                LOGGER.warning("Could not get trips for area " + areaId + ". Exception: " + e);
                continue;
            }
            // Some trips might be in several areas
            if (!isDuplicatedTrip(trips, trip)) {
                trips.add(trip);
            }
        }
        if (trips.isEmpty()) {
            LOGGER.severe("No trips found.");
            return;
        }

        LOGGER.info("I found " + trips.size() + " trips that could interest you!");
        for(Iterator<Trip> tripsIte = trips.iterator(); tripsIte.hasNext();) {
            prettyPrint(tripsIte.next());
        }
    }

    private static boolean isDuplicatedTrip(final Set<Trip> trips, final Trip newTrip) {
        for(final Trip trip:trips) {
            if (trip.getId().equals(newTrip.getId())) {
                return true;
            }
        }
        return false;
    }

    private static void prettyPrint(final Trip trip) {
        System.out.println("===");
        System.out.println("name: " + trip.getName());
        System.out.println("description: " + trip.getDescription());
        System.out.println("distance: " + trip.getDistance()/1000 + "km");
        System.out.println("normal time spent: " + trip.getTimeSpent().getNormal());
        System.out.println("links: " + trip.getLinks());
        System.out.println("url: " + trip.getUrl());
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
