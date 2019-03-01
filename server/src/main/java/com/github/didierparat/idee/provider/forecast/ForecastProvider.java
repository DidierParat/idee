package com.github.didierparat.idee.provider.forecast;

import com.github.didierparat.idee.provider.forecast.model.ProviderWeather;

import java.util.Calendar;

public interface ForecastProvider {

    ProviderWeather getWeather(final String longitude, final String latitude, final Calendar date);
}
