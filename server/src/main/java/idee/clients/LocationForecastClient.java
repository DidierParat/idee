package idee.clients;

import idee.models.Forecast;
import idee.models.nasjonalturbase.Area;
import org.apache.http.client.utils.URIBuilder;

import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.logging.Logger;

public class LocationForecastClient extends AdaptiveClient {

  private static final String API_VERSION = "1.9";
  private static final String API_URL = "https://api.met.no/weatherapi/locationforecast";
  private static final Logger LOGGER = Logger.getLogger(LocationForecastClient.class.getName());

  public LocationForecastClient() {
    super();
  }

  public Forecast getWeather(final Area area, final Calendar day)
      throws ClientException {
    final String lon = area.getCenter().getLongitude().toString();
    final String lat = area.getCenter().getLatitude().toString();
    final URIBuilder requestUrl;
    try {
      requestUrl = createRequest(lon, lat);
    } catch (URISyntaxException exception) {
      throw new ClientException("Could not create weather request.", exception);
    }
    final String xmlForecast = getData(requestUrl, String.class);
    final Forecast forecast;
    forecast = new Forecast(area, day, xmlForecast);
    return forecast;
  }

  private URIBuilder createRequest(final String lon, final String lat) throws URISyntaxException {
    return new URIBuilder(
        API_URL + "/" + API_VERSION + "/?" + "lon=" + lon + ";" + "lat=" + lat);
  }
}
