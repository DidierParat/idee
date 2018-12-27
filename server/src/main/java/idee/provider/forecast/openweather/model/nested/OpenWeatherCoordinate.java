package idee.provider.forecast.openweather.model.nested;

import lombok.Value;

@Value
public class OpenWeatherCoordinate {

  private final float lon;
  private final float lat;
}
