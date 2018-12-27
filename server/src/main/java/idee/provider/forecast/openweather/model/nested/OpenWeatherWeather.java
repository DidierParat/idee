package idee.provider.forecast.openweather.model.nested;

import lombok.Value;

@Value
public class OpenWeatherWeather {

  private final int id;
  private final String main;
  private final String description;
  private final String icon;
}
