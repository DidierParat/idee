package idee;

import idee.Nasjonalturbase.Area;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by didier on 19.01.17.
 */
public class Forecast {
    private static final Logger LOGGER = Logger.getLogger(Forecast.class.getName());
    private static final String PRODUCT_CHILD_NAME = "product";
    private static final String LOCATION_CHILD_NAME = "location";
    private static final String CLOUDINESS_CHILD_NAME = "cloudiness";
    private static final String DATA_TYPE_ATTRIBUTE = "datatype";
    private static final String DATA_TYPE_ATTRIBUTE_EXPECTED_VALUE = "forecast";
    private static final String FROM_ATTRIBUTE = "from";
    private static final String TO_ATTRIBUTE = "to";
    private static final String PERCENT_ATTRIBUTE = "percent";

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
    public Forecast(final Area area, final Calendar day, final String xmlForecast)
            throws JDOMException, IOException {
        this.area = area;
        this.weather = extractWeatherFromXml(day, xmlForecast);
    }

    private String formatDateForApi(final Calendar day) {
        // Format from the API: 2017-03-03T12:00:00Z
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(day.getTime()) + "T12:00:00Z";
    }

    // TODO support rainy and snowy
    private Weather extractWeatherFromXml(final Calendar day, final String xmlForecast)
            throws JDOMException, IOException {
        final String formattedDate = formatDateForApi(day);
        final org.jdom.input.SAXBuilder saxBuilder = new SAXBuilder();
        final org.jdom.Document document = saxBuilder.build(new StringReader(xmlForecast));
        final Element rootNode = document.getRootElement();
        final List list = rootNode.getChild(PRODUCT_CHILD_NAME).getChildren();
        // Have to parse the list of <time> now
        Iterator<List> listIte = list.iterator();
        while (listIte.hasNext()) {
            final Element element = (Element) listIte.next();
            if (!element.getAttribute(DATA_TYPE_ATTRIBUTE).getValue()
                    .equals(DATA_TYPE_ATTRIBUTE_EXPECTED_VALUE)) {
                continue;
            }
            if (!element.getAttribute(FROM_ATTRIBUTE).getValue().equals(formattedDate)) {
                continue;
            }
            if (!element.getAttribute(TO_ATTRIBUTE).getValue().equals(formattedDate)) {
                continue;
            }
            Element cloudiness
                    = element.getChild(LOCATION_CHILD_NAME).getChild(CLOUDINESS_CHILD_NAME);
            Float cloudinessPercent
                    = Float.valueOf(cloudiness.getAttribute(PERCENT_ATTRIBUTE).getValue());
            if (cloudinessPercent < 25.0) {
                return Weather.Sunny;
            }
            if (cloudinessPercent < 65.0) {
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
