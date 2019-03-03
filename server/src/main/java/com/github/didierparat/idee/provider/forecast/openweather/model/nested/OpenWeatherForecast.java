package com.github.didierparat.idee.provider.forecast.openweather.model.nested;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Value;

import java.util.List;

@Value
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherForecast {

  private final long dt;
  private final OpenWeatherTemperature temp;
  private final float pressure;
  private final int humidity;
  private final List<OpenWeatherWeather> weather;
}
