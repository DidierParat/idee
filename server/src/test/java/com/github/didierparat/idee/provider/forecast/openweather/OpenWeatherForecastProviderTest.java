package com.github.didierparat.idee.provider.forecast.openweather;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.github.didierparat.idee.TestUtil;
import com.github.didierparat.idee.provider.client.AdaptiveClient;
import com.github.didierparat.idee.provider.forecast.model.ProviderForecast;
import com.github.didierparat.idee.provider.forecast.model.nested.ProviderWeatherMain;
import com.github.didierparat.idee.provider.forecast.openweather.model.OpenWeatherDayForecast;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.URI;
import java.util.Calendar;

@RunWith(MockitoJUnitRunner.class)
public class OpenWeatherForecastProviderTest {

  private static final String LONGITUDE = "1.0";
  private static final String LATITUDE = "1.0";
  private static final String COUNT = "7";
  private static final String HOST = "https://api.openweathermap.org/data/2.5";
  private static final String API_KEY = "123";

  private OpenWeatherForecastProvider openWeatherForecastProvider;
  @Mock
  private AdaptiveClient adaptiveClient;

  @Before
  public void setUp() {
    this.openWeatherForecastProvider = new OpenWeatherForecastProvider(
        HOST, API_KEY, adaptiveClient);
  }

  @Test
  public void getWeather_ReturnsWeather() throws Exception {
    URI uri = createUri();
    OpenWeatherDayForecast openWeatherDayForecast = TestUtil.readValue(
        TestUtil.RESOURCE_OPENWEATHER_PROVIDER_DAY_FORECAST, OpenWeatherDayForecast.class);
    Calendar day = Calendar.getInstance();
    day.setTimeInMillis(openWeatherDayForecast.getList().get(4).getDt());
    when(adaptiveClient.getData(eq(uri), eq(OpenWeatherDayForecast.class)))
        .thenReturn(openWeatherDayForecast);
    ProviderForecast providerForecast = openWeatherForecastProvider.getWeather(
        LONGITUDE, LATITUDE, day);

    assertNotNull(providerForecast);
    assertEquals(ProviderWeatherMain.SUNNY, providerForecast.getMain());
  }

  private URI createUri() throws Exception {
     return new URI(
         String.format(
             "%s/forecast/daily?lon=%s&lat=%s&cnt=%s&appid=%s",
             HOST,
             LONGITUDE,
             LATITUDE,
             COUNT,
             API_KEY));

  }
}
