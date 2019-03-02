package com.github.didierparat.idee.provider.forecast;

import com.github.didierparat.idee.provider.forecast.model.ProviderForecast;

import java.util.Calendar;

public interface ForecastProvider {

    ProviderForecast getWeather(final String longitude, final String latitude, final Calendar date);
}
