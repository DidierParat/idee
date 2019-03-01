package com.github.didierparat.idee.service;

import com.github.didierparat.idee.model.Weather;
import com.github.didierparat.idee.provider.forecast.model.ProviderWeather;
import com.github.didierparat.idee.provider.forecast.ForecastProvider;
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
