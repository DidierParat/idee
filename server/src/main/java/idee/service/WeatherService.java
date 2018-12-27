package idee.service;

import idee.model.Weather;
import idee.provider.forecast.ForecastProvider;
import idee.provider.forecast.model.ProviderWeather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class WeatherService {

  private final ForecastProvider forecastProvider;

  @Autowired
  public WeatherService(final ForecastProvider forecastProvider) {
    this.forecastProvider = forecastProvider;
  }

  public Weather getWeather(final String longitude, final String latitude, final Calendar date) {
    return providerWeatherToWeather(
        forecastProvider.getWeather(longitude, latitude, date));
  }

  private Weather providerWeatherToWeather(final ProviderWeather providerWeather) {
    final Weather weather = null;
    // TODO
    return weather;
  }
}
