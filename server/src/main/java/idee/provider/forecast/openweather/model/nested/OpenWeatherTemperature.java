package idee.provider.forecast.openweather.model.nested;

import lombok.Value;

@Value
public class OpenWeatherTemperature {

  private final float day;
  private final float min;
  private final float max;
  private final float night;
  private final float eve;
  private final float morn;
}
