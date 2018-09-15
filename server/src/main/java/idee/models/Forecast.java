package idee.models;

import idee.models.Nasjonalturbase.Area;
import org.jdom.JDOMException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class Forecast {

  private static final Logger LOGGER = Logger.getLogger(Forecast.class.getName());
  private static final String PRODUCT = "product";
  private static final String TIME = "time";
  private static final String LOCATION = "location";
  private static final String CLOUDINESS = "cloudiness";
  private static final String DATA_TYPE = "datatype";
  private static final String DATA_TYPE_EXPECTED_VALUE = "forecast";
  private static final String FROM = "from";
  private static final String TO = "to";
  private static final String PERCENT = "percent";

  // Order is important to compare weather. From Sunny (0) best to rainy (3) worst
  public enum Weather {
    Sunny,
    PartiallyCloudy,
    Cloudy,
    Rainy,
    Snowy,
    Unknown
  }

  private final Weather weather;
  private final Area area;

  public Forecast(final Area area, final Calendar day, final String jsonForecast)
      throws JDOMException, IOException {
    this.area = area;
    this.weather = extractWeatherFromXml(day, jsonForecast);
  }

  private String formatDateForApi(final Calendar day) {
    // Format from the API: 2017-03-03T12:00:00Z
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    return format.format(day.getTime()) + "T12:00:00Z";
  }

  // TODO support rainy and snowy
  private Weather extractWeatherFromXml(final Calendar day, final String jsonForecast) {
    final String formattedDate = formatDateForApi(day);
    final JSONObject document = new JSONObject(jsonForecast);
    final JSONArray forecastByTime = document.getJSONObject(PRODUCT).getJSONArray(TIME);
    for (int index = 0; index < forecastByTime.length(); index++) {
      final JSONObject element = forecastByTime.getJSONObject(index);
      if (!element.getString(DATA_TYPE).equals(DATA_TYPE_EXPECTED_VALUE)) {
        continue;
      }
      if (!element.getString(FROM).equals(formattedDate)) {
        continue;
      }
      if (!element.getString(TO).equals(formattedDate)) {
        continue;
      }
      final String cloudinessString = element.getJSONObject(LOCATION).getJSONObject(CLOUDINESS).getString(PERCENT);
      final Float cloudinessFloat = Float.parseFloat(cloudinessString);
      if (cloudinessFloat < 25.0) {
        return Weather.Sunny;
      }
      if (cloudinessFloat < 65.0) {
        return Weather.PartiallyCloudy;
      }
      return Weather.Cloudy;
    }
    return Weather.Unknown;
  }

  public Weather getWeather() {
    return weather;
  }

  public Area getArea() {
    return area;
  }
}
