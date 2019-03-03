package com.github.didierparat.idee.model;

import com.github.didierparat.idee.model.nested.WeatherMain;
import lombok.Value;

@Value
public class Forecast {
  private final WeatherMain main;
}
