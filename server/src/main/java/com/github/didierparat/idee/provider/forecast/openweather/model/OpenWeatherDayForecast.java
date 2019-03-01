package com.github.didierparat.idee.provider.forecast.openweather.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.didierparat.idee.provider.forecast.openweather.model.nested.OpenWeatherForecast;
import com.github.didierparat.idee.provider.forecast.openweather.model.nested.OpenWeatherCoordinate;
import lombok.Value;

import java.util.List;

@Value
@JsonInclude(Include.NON_NULL)
public class OpenWeatherDayForecast {

  private final OpenWeatherCoordinate coord;
  private final List<OpenWeatherForecast> list;
}
