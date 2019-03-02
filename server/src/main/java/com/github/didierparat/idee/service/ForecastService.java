package com.github.didierparat.idee.service;

import com.github.didierparat.idee.model.Forecast;
import com.github.didierparat.idee.model.nested.WeatherMain;
import com.github.didierparat.idee.provider.forecast.model.ProviderForecast;
import com.github.didierparat.idee.provider.forecast.ForecastProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class ForecastService {

  private final ForecastProvider forecastProvider;

  @Autowired
  public ForecastService(final ForecastProvider forecastProvider) {
    this.forecastProvider = forecastProvider;
  }

  public Forecast getWeather(final String longitude, final String latitude, final Calendar date) {
    return providerWeatherToWeather(
        forecastProvider.getWeather(longitude, latitude, date));
  }

  private Forecast providerWeatherToWeather(final ProviderForecast providerForecast) {
    return new Forecast(WeatherMain.valueOf(providerForecast.getMain().name()));
  }
}
