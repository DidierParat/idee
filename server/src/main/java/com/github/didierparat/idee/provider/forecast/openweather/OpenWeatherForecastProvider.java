package com.github.didierparat.idee.provider.forecast.openweather;

import com.github.didierparat.idee.provider.ProviderException;
import com.github.didierparat.idee.provider.forecast.model.nested.ProviderWeatherMain;
import com.github.didierparat.idee.provider.forecast.openweather.model.nested.OpenWeatherForecast;
import com.github.didierparat.idee.provider.client.AdaptiveClient;
import com.github.didierparat.idee.provider.client.ClientException;
import com.github.didierparat.idee.provider.forecast.ForecastProvider;
import com.github.didierparat.idee.provider.forecast.openweather.model.OpenWeatherDayForecast;
import com.github.didierparat.idee.provider.forecast.openweather.model.nested.OpenWeatherWeather;
import com.github.didierparat.idee.provider.forecast.model.ProviderWeather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class OpenWeatherForecastProvider implements ForecastProvider {

  // Open Weather constants
  private static final String DAILY_FORECAST_PATH = "forecast/daily";
  private static final String QUERY_PARAM_LONGITUDE = "lon";
  private static final String QUERY_PARAM_LATITUDE = "lat";
  private static final String QUERY_PARAM_COUNT = "cnt";
  private static final String QUERY_PARAM_APP_ID = "lon";

  private static final int NUMBER_OF_DAYS_TO_FETCH = 7;

  private final String host;
  private final String apiKey;
  private final AdaptiveClient adaptiveClient;

  @Autowired
  public OpenWeatherForecastProvider(
      @Value("${openweather.host}") final String host,
      @Value("${openweather.apikey}") final String apiKey,
      final AdaptiveClient adaptiveClient) {
    this.host = host;
    this.apiKey = apiKey;
    this.adaptiveClient = adaptiveClient;
  }

  public ProviderWeather getWeather(
      final String longitude, final String latitude, final Calendar day) {
    final OpenWeatherDayForecast openWeatherDayForecast = fetchWeather(longitude, latitude);
    return convertToForecast(openWeatherDayForecast, day);
  }

  private OpenWeatherDayForecast fetchWeather(final String lon, final String lat) {
    final URI requestUrl
        = UriComponentsBuilder
        .fromUriString(host)
        .path(DAILY_FORECAST_PATH)
        .queryParam(QUERY_PARAM_LONGITUDE, lon)
        .queryParam(QUERY_PARAM_LATITUDE, lat)
        .queryParam(QUERY_PARAM_COUNT, NUMBER_OF_DAYS_TO_FETCH)
        .queryParam(QUERY_PARAM_APP_ID, apiKey)
        .build()
        .toUri();
    try {
      return adaptiveClient.getData(requestUrl, OpenWeatherDayForecast.class);
    } catch (ClientException exception) {
      throw new ProviderException("Exception thrown while getting forecast.", exception);
    }
  }

  private ProviderWeather convertToForecast(
      final OpenWeatherDayForecast openWeatherDayForecast, final Calendar day) {
    for (final OpenWeatherForecast openWeatherForecast : openWeatherDayForecast.getList()) {
      if (sameDay(openWeatherForecast.getDt(), day)) {
        return getOpenWeatherWeatherFromProviderWeather(openWeatherForecast.getWeather());
      }
    }
    return new ProviderWeather(ProviderWeatherMain.UNKNOWN);
  }

  private boolean sameDay(final long epoch, final Calendar targetDay) {
    final Date dateFromEpoch = new Date(epoch);
    DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    String formattedDateFromEpoch = format.format(dateFromEpoch);
    String formattedTargetDay = format.format(targetDay);
    return formattedTargetDay.equals(formattedDateFromEpoch);
  }

  // TODO complete
  private ProviderWeather getOpenWeatherWeatherFromProviderWeather(
      final OpenWeatherWeather weather) {
    switch (weather.getMain()) {
      case "Clear":
        return new ProviderWeather(ProviderWeatherMain.SUNNY);
      case "Rain":
        return new ProviderWeather(ProviderWeatherMain.RAINY);
      default:
        return new ProviderWeather(ProviderWeatherMain.UNKNOWN);
    }
  }
}
