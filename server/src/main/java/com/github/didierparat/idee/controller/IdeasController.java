package com.github.didierparat.idee.controller;

import com.google.common.annotations.VisibleForTesting;

import com.github.didierparat.idee.model.Forecast;
import com.github.didierparat.idee.model.Idea;
import com.github.didierparat.idee.model.Trip;
import com.github.didierparat.idee.model.nested.WeatherMain;
import com.github.didierparat.idee.service.ForecastService;
import com.github.didierparat.idee.service.TripService;
import com.github.didierparat.idee.validator.IdeaInputValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
  @VisibleForTesting
  static final String QUERY_PARAM_DATE = "date";

  private final ForecastService forecastService;
  private final TripService tripService;

  @Autowired
  public IdeasController(
      final ForecastService forecastService,
      final TripService tripService) {
    this.forecastService = forecastService;
    this.tripService = tripService;
  }

  @GetMapping(path = GET_IDEAS_PATH)
  public List<Idea> getIdeas(
      @RequestParam(QUERY_PARAM_USER_LONGITUDE) final String userLocationLongitude,
      @RequestParam(QUERY_PARAM_USER_LATITUDE) final String userLocationLatitude,
      @RequestParam(QUERY_PARAM_SEARCH_RADIUS) final String searchRadius,
      @RequestParam(QUERY_PARAM_DATE) final String dateAsString) throws ParseException {
    IdeaInputValidator.validate(
        userLocationLongitude, userLocationLatitude, searchRadius, dateAsString);

    final Forecast userForecastPreference = new Forecast(WeatherMain.RAINY);
    final Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateAsString);

    final List<Trip> trips = tripService.getTripsInArea(
        userLocationLongitude, userLocationLatitude, searchRadius);

    final List<Idea> ideas = new ArrayList<>(trips.size());
    for(final Trip trip : trips) {
      final String tripLongitude = trip.getLocation().getLongitude().toString();
      final String tripLatitude = trip.getLocation().getLatitude().toString();
      final Forecast forecast = forecastService.getWeather(tripLongitude, tripLatitude, date);
      ideas.add(new Idea(forecast, trip));
    }

    ideas.removeIf(
        idea -> idea.getForecast().getMain().compareTo(userForecastPreference.getMain()) > 0);

    log.info("I found {} Ideas!", ideas.size());
    ideas.forEach(idea -> log.debug(idea.toString()));
    return ideas;
  }
}
