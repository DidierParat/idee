package com.github.didierparat.idee.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.github.didierparat.idee.TestUtil;
import com.github.didierparat.idee.model.Forecast;
import com.github.didierparat.idee.provider.forecast.ForecastProvider;
import com.github.didierparat.idee.provider.forecast.model.ProviderForecast;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

@RunWith(MockitoJUnitRunner.class)
public class ForecastServiceTest {

  private static final String LONGITUDE = "1.0";
  private static final String LATITUDE = "1.0";

  private ForecastService forecastService;
  @Mock
  private ForecastProvider forecastProvider;

  @Before
  public void setUp() {
    this.forecastService = new ForecastService(forecastProvider);
  }

  @Test
  public void getWeather_ReturnWeather() throws Exception {
    ProviderForecast providerForecast = TestUtil.readValue(
        TestUtil.RESOURCE_PROVIDER_FORECAST, ProviderForecast.class);
    when(forecastProvider.getWeather(eq(LONGITUDE), eq(LATITUDE), any(Date.class)))
        .thenReturn(providerForecast);

    Forecast forecast = forecastService.getWeather(LONGITUDE, LATITUDE, new Date());

    assertNotNull(forecast);
    assertEquals(providerForecast.getMain().name(), forecast.getMain().name());
  }
}
