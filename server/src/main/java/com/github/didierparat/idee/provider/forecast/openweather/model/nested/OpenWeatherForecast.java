package com.github.didierparat.idee.provider.forecast.openweather.model.nested;

import lombok.Value;

@Value
public class OpenWeatherForecast {

  private final long dt;
  private final OpenWeatherTemperature temp;
  private final float pressure;
  private final int humidity;
  private final OpenWeatherWeather weather;
}
