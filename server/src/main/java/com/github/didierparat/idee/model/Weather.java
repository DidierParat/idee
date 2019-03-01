package com.github.didierparat.idee.model;

import com.github.didierparat.idee.model.nested.WeatherMain;
import lombok.Data;

@Data
public class Weather {
  private final WeatherMain main;
}
