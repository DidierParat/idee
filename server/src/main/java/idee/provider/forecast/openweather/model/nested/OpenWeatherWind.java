package idee.provider.forecast.openweather.model.nested;

import lombok.Value;

@Value
public class OpenWeatherWind {

  private final float speed;
  private final int deg;
}
