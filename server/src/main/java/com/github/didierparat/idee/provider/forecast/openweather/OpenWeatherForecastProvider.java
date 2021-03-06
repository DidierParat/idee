package com.github.didierparat.idee.provider.forecast.openweather;

import com.github.didierparat.idee.provider.ProviderException;
import com.github.didierparat.idee.provider.forecast.model.nested.ProviderWeatherMain;
import com.github.didierparat.idee.provider.forecast.openweather.model.nested.OpenWeatherForecast;
import com.github.didierparat.idee.provider.client.AdaptiveClient;
import com.github.didierparat.idee.provider.client.ClientException;
import com.github.didierparat.idee.provider.forecast.ForecastProvider;
import com.github.didierparat.idee.provider.forecast.openweather.model.OpenWeatherDayForecast;
import com.github.didierparat.idee.provider.forecast.openweather.model.nested.OpenWeatherWeather;
import com.github.didierparat.idee.provider.forecast.model.ProviderForecast;
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

  // Open Forecast constants
  private static final String DAILY_FORECAST_PATH = "/forecast";
  private static final String QUERY_PARAM_LONGITUDE = "lon";
  private static final String QUERY_PARAM_LATITUDE = "lat";
  private static final String QUERY_PARAM_COUNT = "cnt";
  private static final String QUERY_PARAM_APP_ID = "appid";

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

  public ProviderForecast getWeather(
      final String longitude, final String latitude, final Date date) {
    final OpenWeatherDayForecast openWeatherDayForecast = fetchWeather(longitude, latitude);
    return convertToForecast(openWeatherDayForecast, date);
  }

  private OpenWeatherDayForecast fetchWeather(final String lon, final String lat) {
    final URI requestUrl
        = UriComponentsBuilder
        .fromUriString(host)
        .path(DAILY_FORECAST_PATH)
        .queryParam(QUERY_PARAM_LONGITUDE, lon)
        .queryParam(QUERY_PARAM_LATITUDE, lat)
        .queryParam(QUERY_PARAM_APP_ID, apiKey)
        .build()
        .toUri();
    OpenWeatherDayForecast openWeatherDayForecast;
    try {
      openWeatherDayForecast = adaptiveClient.getData(requestUrl, OpenWeatherDayForecast.class);
    } catch (ClientException exception) {
      throw new ProviderException("Exception thrown while getting forecast.", exception);
    }
    if (openWeatherDayForecast == null) {
      throw new ProviderException("Got null value from Open Weather.");
    }
    return openWeatherDayForecast;
  }

  private ProviderForecast convertToForecast(
      final OpenWeatherDayForecast openWeatherDayForecast, final Date date) {
    for (final OpenWeatherForecast openWeatherForecast : openWeatherDayForecast.getList()) {
      if (sameDay(openWeatherForecast.getDt()*1000, date)) {
        return getOpenWeatherWeatherFromProviderWeather(openWeatherForecast.getWeather().get(0));
      }
    }
    return new ProviderForecast(ProviderWeatherMain.UNKNOWN);
  }

  private boolean sameDay(final long timeInMs, final Date date) {
    final Date dateFromEpoch = new Date(timeInMs);
    DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    String formattedDateFromEpoch = format.format(dateFromEpoch);
    String formattedTargetDay = format.format(date.getTime());
    return formattedTargetDay.equals(formattedDateFromEpoch);
  }

  // TODO complete
  private ProviderForecast getOpenWeatherWeatherFromProviderWeather(
      final OpenWeatherWeather weather) {
    switch (weather.getMain()) {
      case "Clear":
        return new ProviderForecast(ProviderWeatherMain.SUNNY);
      case "Clouds":
        return new ProviderForecast(ProviderWeatherMain.CLOUDY);
      case "Rain":
        return new ProviderForecast(ProviderWeatherMain.RAINY);
      default:
        return new ProviderForecast(ProviderWeatherMain.UNKNOWN);
    }
  }
}
