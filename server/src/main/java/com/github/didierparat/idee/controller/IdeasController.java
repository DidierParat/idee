package com.github.didierparat.idee.controller;

import com.google.common.annotations.VisibleForTesting;

import com.github.didierparat.idee.model.Idea;
import com.github.didierparat.idee.model.Trip;
import com.github.didierparat.idee.model.Weather;
import com.github.didierparat.idee.model.nested.WeatherMain;
import com.github.didierparat.idee.service.TripService;
import com.github.didierparat.idee.service.WeatherService;
import com.github.didierparat.idee.util.CalendarUtil;
import com.github.didierparat.idee.validator.IdeaInputValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class IdeasController {

  @VisibleForTesting
  static final String GET_IDEAS_PATH = "/ideas";
  @VisibleForTesting
  static final String QUERY_PARAM_USER_LONGITUDE = "lon";
  @VisibleForTesting
  static final String QUERY_PARAM_USER_LATITUDE = "lat";
  @VisibleForTesting
  static final String QUERY_PARAM_SEARCH_RADIUS = "searchRadius";

  private final WeatherService weatherService;
  private final TripService tripService;

  @Autowired
  public IdeasController(
      final WeatherService weatherService,
      final TripService tripService) {
    this.weatherService = weatherService;
    this.tripService = tripService;
  }

  @GetMapping(path = GET_IDEAS_PATH)
  public List<Idea> getIdeas(
      @RequestParam(QUERY_PARAM_USER_LONGITUDE) final String userLocationLongitude,
      @RequestParam(QUERY_PARAM_USER_LATITUDE) final String userLocationLatitude,
      @RequestParam(QUERY_PARAM_SEARCH_RADIUS) final String searchRadius) {
    IdeaInputValidator.validate(userLocationLongitude, userLocationLatitude, searchRadius);

    final Weather userWeatherPreference = new Weather(WeatherMain.SUNNY);
    final Calendar saturday = CalendarUtil.getNextSaturday();

    final List<Trip> trips = tripService.getTripsInArea(
        userLocationLongitude, userLocationLatitude, searchRadius);

    final List<Idea> ideas = new ArrayList<>(trips.size());
    for(final Trip trip : trips) {
      final String tripLongitude = trip.getLocation().getLongitude().toString();
      final String tripLatitude = trip.getLocation().getLatitude().toString();
      final Weather weather = weatherService.getWeather(tripLongitude, tripLatitude, saturday);
      ideas.add(new Idea(weather, trip));
    }

    ideas.removeIf(
        idea -> idea.getWeather().getMain().compareTo(userWeatherPreference.getMain()) > 0);

    log.info("I found {} Ideas!", ideas.size());
    ideas.forEach(idea -> log.debug(idea.toString()));
    return ideas;
  }
}
