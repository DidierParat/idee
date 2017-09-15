package idee;

import idee.Nasjonalturbase.*;
import org.json.*;

public class Idea {
    private static final String FIELD_DISTANCE = "distance";
    private static final String FIELD_DURATION = "duration";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_URL = "url";
    private static final String FIELD_WEATHER_FORECAST = "weather_forecast";

    private final Forecast forecast;
    private final Trip trip;

    public Idea(final Trip trip, final Forecast forecast) {
        this.trip = trip;
        this.forecast = forecast;
    }

    public JSONObject toJsonObject() {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put(FIELD_NAME, trip.getName());
        jsonObject.put(FIELD_DISTANCE, trip.getDistance());
        jsonObject.put(FIELD_DURATION, trip.getTimeSpent().getNormal());
        jsonObject.put(FIELD_WEATHER_FORECAST, forecast.getWeather().name());
        jsonObject.put(FIELD_URL, trip.getUrl());
        return jsonObject;
    }

    public Trip getTrip() {
        return trip;
    }

    public Forecast getForecast() {
        return forecast;
    }
}
