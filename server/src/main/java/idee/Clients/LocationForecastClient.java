package idee.Clients;

import idee.Forecast;
import idee.Nasjonalturbase.Area;
import org.jdom.JDOMException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.logging.Logger;

/**
 * Created by didier on 19.01.17.
 */
public class LocationForecastClient {
    private static final String API_VERSION = "1.9";
    private static final String API_URL = "http://api.met.no/weatherapi/locationforecast";
    private static final Logger LOGGER = Logger.getLogger(LocationForecastClient.class.getName());
    private final AdaptiveClient adaptiveClient;

    public LocationForecastClient(final AdaptiveClient adaptiveClient) {
        this.adaptiveClient = adaptiveClient;
    }

    public Forecast getWeather(final Area area, final Calendar day)
            throws URISyntaxException,
            IOException,
            AdaptiveClient.RateLimitExceededException,
            JDOMException {
        final String lon = area.getCenter().getLongitude().toString();
        final String lat = area.getCenter().getLatitude().toString();
        final URI requestUrl = createRequestUri(lon, lat);
        final String xmlForecast = adaptiveClient.getData(requestUrl, String.class);
        final Forecast forecast = new Forecast(area, day, xmlForecast);
        return forecast;
    }

    private URI createRequestUri(final String lon, final String lat) throws URISyntaxException {
        URI uri = new URI(API_URL + "/"
                    + API_VERSION + "/?"
                    + "lon=" + lon + ";"
                    + "lat=" + lat);
        return uri;
    }
}
