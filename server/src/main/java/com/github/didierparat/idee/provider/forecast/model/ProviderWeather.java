package com.github.didierparat.idee.provider.forecast.model;

import com.github.didierparat.idee.provider.forecast.model.nested.ProviderWeatherMain;
import lombok.Data;

@Data
public class ProviderWeather {

  private final ProviderWeatherMain main;
}
