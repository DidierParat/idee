package com.github.didierparat.idee.provider.forecast.openweather.model.nested;

import lombok.Value;

@Value
public class OpenWeatherMain {

  private final float temp;
  private final int pressure;
  private final int humidity;
  private final float temp_min;
  private final float temp_max;
}
