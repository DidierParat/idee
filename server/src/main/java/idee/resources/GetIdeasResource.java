package idee.resources;

import idee.clients.ClientException;
import idee.clients.DntClient;
import idee.clients.LocationForecastClient;
import idee.models.Forecast;
import idee.models.Idea;
import idee.models.Location;
import idee.models.nasjonalturbase.Area;
import idee.models.nasjonalturbase.Trip;
import idee.utils.MapOfNorway;
import org.json.JSONArray;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class GetIdeasResource {

  private static final Logger LOGGER = Logger.getLogger(GetIdeasResource.class.getName());
  private static final String PATH_GET_IDEAS = "get-ideas";
  private static final String QUERRY_PARAM_USER_LONGITUDE = "userLon";
  private static final String QUERRY_PARAM_USER_LATITUDE = "userLat";
  private static final String QUERRY_PARAM_SEARCH_RADIUS = "searchRadius";

  private final DntClient dntClient;
  private final LocationForecastClient locationForecastClient;
  private final MapOfNorway mapOfNorway;

  public GetIdeasResource(
      final DntClient dntClient,
      final LocationForecastClient locationForecastClient,
      final MapOfNorway mapOfNorway) {
    this.dntClient = dntClient;
    this.locationForecastClient = locationForecastClient;
    this.mapOfNorway = mapOfNorway;
  }

  @POST
  @Path(PATH_GET_IDEAS)
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Produces(MediaType.APPLICATION_JSON)
  public String getIdeas(
      @FormParam(QUERRY_PARAM_USER_LONGITUDE) final String userLocationLon,
      @FormParam(QUERRY_PARAM_USER_LATITUDE) final String userLocationLat,
      @FormParam(QUERRY_PARAM_SEARCH_RADIUS) final String searchRadius) {
    final Location userLocation =
        new Location(
            Double.parseDouble(userLocationLon), Double.parseDouble(userLocationLat));
    final Integer searchRadiusKm = Integer.parseInt(searchRadius);
    final Forecast.Weather userWeatherPreference = Forecast.Weather.Rainy;
    final Calendar saturday = getNextSaturday();

    // Get areas nearby
    final Set<Area> areasNearby = mapOfNorway.getNearbyAreas(userLocation, searchRadiusKm);
    if (areasNearby.isEmpty()) {
      LOGGER.info("No area found nearby the provided location.");
      return createJsonResponse(Collections.emptySet());
    }

    // Get weather of nearby cities and filter cities on weather
    final Set<Forecast> forecastNearbyAreas = new HashSet<>();
    for (final Area area : areasNearby) {
      try {
        final Forecast forecast = locationForecastClient.getWeather(area, saturday);
        if (forecast.getWeather().ordinal() <= userWeatherPreference.ordinal()) {
          forecastNearbyAreas.add(forecast);
        }
      } catch (ClientException exception) {
        LOGGER.log(Level.WARNING, "Could not get forecast for area " + area.getId(), exception);
        return createJsonResponse(Collections.emptySet());
      }
    }
    if (forecastNearbyAreas.isEmpty()) {
      LOGGER.log(Level.INFO, "No area with weather forecast corresponding to user's preferences.");
      return createJsonResponse(Collections.emptySet());
    }

    // Get suggested trips in these areas
    final Set<Idea> ideas = new HashSet<>();
    for (final Forecast forecast : forecastNearbyAreas) {
      final Area area = forecast.getArea();
      final Trip trip;
      final String areaId = area.getId();
      try {
        trip = dntClient.getTripPerArea(areaId);
      } catch (ClientException exception) {
        LOGGER.log(Level.WARNING, "Could not get trips for area " + areaId, exception);
        continue;
      }
      // Some trips might be in several areas
      if (!isDuplicatedTrip(ideas, trip)) {
        ideas.add(new Idea(trip, forecast));
      }
    }
    if (ideas.isEmpty()) {
      LOGGER.warning("No Idea found.");
      return createJsonResponse(Collections.emptySet());
    }

    LOGGER.info("I found " + ideas.size() + " Ideas!");
    for (final Idea idea : ideas) {
      prettyPrint(idea);
    }
    final String response = createJsonResponse(ideas);
    LOGGER.info(response);
    return response;
  }


  private static boolean isDuplicatedTrip(final Set<Idea> ideas, final Trip newTrip) {
    for (final Idea idea : ideas) {
      if (idea.getTrip().getId().equals(newTrip.getId())) {
        return true;
      }
    }
    return false;
  }

  private static String createJsonResponse(final Set<Idea> ideas) {
    JSONArray response = new JSONArray();
    for (final Idea idea : ideas) {
      response.put(idea.toJsonObject());
    }
    return response.toString();

  }

  private static void prettyPrint(final Idea idea) {
    System.out.println("===");
    System.out.println("name: " + idea.getTrip().getName());
    System.out.println("description: " + idea.getTrip().getDescription());
    System.out.println("distance: " + idea.getTrip().getDistance() / 1000 + "km");
    System.out.println("normal time spent: " + idea.getTrip().getTimeSpent().getNormal());
    System.out.println("links: " + idea.getTrip().getLinks());
    System.out.println("url: " + idea.getTrip().getUrl());
  }

  private static Calendar getNextSaturday() {
    final Calendar nextSaturday = Calendar.getInstance();
    int currentDay = nextSaturday.get(Calendar.DAY_OF_WEEK);
    int daysBeforeSaturday = Calendar.SATURDAY - currentDay;
    nextSaturday.add(Calendar.DAY_OF_WEEK, daysBeforeSaturday);
    return nextSaturday;
  }
}
