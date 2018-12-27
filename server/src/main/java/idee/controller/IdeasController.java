package idee.controller;

import idee.model.Idea;
import idee.model.Trip;
import idee.model.Weather;
import idee.service.TripService;
import idee.service.WeatherService;
import idee.util.CalendarUtil;
import idee.validator.IdeaInputValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Slf4j
@RestController
public class IdeasController {

  private static final String QUERRY_PARAM_USER_LONGITUDE = "lon";
  private static final String QUERRY_PARAM_USER_LATITUDE = "lat";
  private static final String QUERRY_PARAM_SEARCH_RADIUS = "searchRadius";

  private final WeatherService weatherService;
  private final TripService tripService;

  @Autowired
  public IdeasController(
      final WeatherService weatherService,
      final TripService tripService) {
    this.weatherService = weatherService;
    this.tripService = tripService;
  }

  @GetMapping(path = "ideas")
  public List<Idea> getIdeas(
      @RequestParam(QUERRY_PARAM_USER_LONGITUDE) final String userLocationLongitude,
      @RequestParam(QUERRY_PARAM_USER_LATITUDE) final String userLocationLatitude,
      @RequestParam(QUERRY_PARAM_SEARCH_RADIUS) final String searchRadius) {
    IdeaInputValidator.validate(userLocationLongitude, userLocationLatitude, searchRadius);

    final Weather userWeatherPreference = Weather.SUNNY;
    final Calendar saturday = CalendarUtil.getNextSaturday();

    final List<Trip> trips = tripService.getTripsInArea(
        userLocationLongitude, userLocationLatitude, searchRadius);

    List<Idea> ideas = new ArrayList<>(trips.size());
    for(final Trip trip : trips) {
      final Weather weather = weatherService.getWeather(
          trip.getLongitude(), trip.getLatitude(), saturday);
      ideas.add(new Idea(weather, trip));
    }

    for(final Idea idea : ideas) {
      if (idea.getWeather().compareTo(userWeatherPreference) < 0) {
        ideas.remove(idea);
      }
    }

    log.info("I found {} Ideas!", ideas.size());
    ideas.forEach(idea -> log.debug(idea.toString()));
    return ideas;
  }
}
