package idee.client;

import idee.model.Forecast;
import idee.model.nasjonalturbase.Area;
import org.apache.http.client.utils.URIBuilder;

import java.net.URISyntaxException;
import java.util.Calendar;

public class LocationForecastClient {

  private static final String API_VERSION = "1.9";
  private static final String API_URL = "https://api.met.no/weatherapi/locationforecast";
  private final AdaptiveClient adaptiveClient;

  public LocationForecastClient() {
    this.adaptiveClient = new AdaptiveClient();
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
    final String xmlForecast = adaptiveClient.getData(requestUrl, String.class);
    final Forecast forecast;
    forecast = new Forecast(area, day, xmlForecast);
    return forecast;
  }

  private URIBuilder createRequest(final String lon, final String lat) throws URISyntaxException {
    return new URIBuilder(API_URL + "/" + API_VERSION)
        .setParameter("lon", lon)
        .setParameter("lat", lat);
  }
}
