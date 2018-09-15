package idee.Clients;

import idee.models.Forecast;
import idee.models.Nasjonalturbase.Area;
import org.apache.http.client.utils.URIBuilder;
import org.jdom.JDOMException;

import java.io.IOException;
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
    } catch (URISyntaxException e) {
      throw new ClientException("Could not create weather request.", e);
    }
    final String xmlForecast = getData(requestUrl, String.class);
    final Forecast forecast;
    try {
      forecast = new Forecast(area, day, xmlForecast);
    } catch (JDOMException | IOException e) {
      throw new ClientException("Could not parse weather.", e);
    }
    return forecast;
  }

  private URIBuilder createRequest(final String lon, final String lat) throws URISyntaxException {
    URIBuilder uriBuilder = new URIBuilder(API_URL + "/"
        + API_VERSION + "/?"
        + "lon=" + lon + ";"
        + "lat=" + lat);
    return uriBuilder;
  }
}
