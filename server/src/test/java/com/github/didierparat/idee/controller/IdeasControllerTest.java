package com.github.didierparat.idee.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.didierparat.idee.TestUtil;
import com.github.didierparat.idee.model.Forecast;
import com.github.didierparat.idee.model.Idea;
import com.github.didierparat.idee.model.Trip;
import com.github.didierparat.idee.service.TripService;
import com.github.didierparat.idee.service.ForecastService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Calendar;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(IdeasController.class)
public class IdeasControllerTest {

  private static final String VALID_LONGITUDE = "";
  private static final String INVALID_LONGITUDE = "";
  private static final String VALID_LATITUDE = "";
  private static final String INVALID_LATITUDE = "";
  private static final String VALID_SEARCH_RADIUS = "";
  private static final String INVALID_SEARCH_RADIUS = "";


  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TripService tripService;

  @MockBean
  private ForecastService forecastService;

  private ObjectMapper objectMapper;

  @Before
  public void setUp() {
    this.objectMapper = new ObjectMapper();
  }

  @Test
  public void getIdeas_ValidInput_Returns200WithIdea() throws Exception {
    List<Trip> trips = TestUtil.readValue(
        TestUtil.RESOURCE_TRIPS, new TypeReference<List<Trip>>() {});
    when(
        tripService.getTripsInArea(
            eq(VALID_LONGITUDE),
            eq(VALID_LATITUDE),
            eq(VALID_SEARCH_RADIUS)))
        .thenReturn(trips);
    Forecast forecastSunny = TestUtil.readValue(TestUtil.RESOURCE_FORECAST_SUNNY, Forecast.class);
    when(forecastService.getWeather(
        eq(trips.get(0).getLocation().getLongitude().toString()),
        eq(trips.get(0).getLocation().getLatitude().toString()),
        any(Calendar.class)))
        .thenReturn(forecastSunny);
    Forecast forecastRainy = TestUtil.readValue(TestUtil.RESOURCE_FORECAST_RAINY, Forecast.class);
    when(forecastService.getWeather(
        eq(trips.get(1).getLocation().getLongitude().toString()),
        eq(trips.get(1).getLocation().getLatitude().toString()),
        any(Calendar.class)))
        .thenReturn(forecastRainy);

    MvcResult result = mockMvc
        .perform(
            get(IdeasController.GET_IDEAS_PATH)
                .param(IdeasController.QUERY_PARAM_USER_LONGITUDE, VALID_LONGITUDE)
                .param(IdeasController.QUERY_PARAM_USER_LATITUDE, VALID_LATITUDE)
                .param(IdeasController.QUERY_PARAM_SEARCH_RADIUS, VALID_SEARCH_RADIUS)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();
    String responseContent = result.getResponse().getContentAsString();

    List<Idea> ideas = objectMapper.readValue(responseContent, new TypeReference<List<Idea>>() {});
    assertNotNull(ideas);
    assertEquals(1, ideas.size());
    verify(tripService, times(1))
        .getTripsInArea(
            eq(VALID_LONGITUDE),
            eq(VALID_LATITUDE),
            eq(VALID_SEARCH_RADIUS));
    verify(forecastService, times(2))
        .getWeather(anyString(), anyString(), any(Calendar.class));
  }

  @Test
  public void getIdeas_MissingLongitude_Returns400() throws Exception {
    mockMvc
        .perform(
            get(IdeasController.GET_IDEAS_PATH)
                .param(IdeasController.QUERY_PARAM_USER_LATITUDE, VALID_LATITUDE)
                .param(IdeasController.QUERY_PARAM_SEARCH_RADIUS, VALID_SEARCH_RADIUS)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andReturn();
  }

  @Test
  public void getIdeas_MissingLatitude_Returns400() throws Exception {
    mockMvc
        .perform(
            get(IdeasController.GET_IDEAS_PATH)
                .param(IdeasController.QUERY_PARAM_USER_LONGITUDE, VALID_LONGITUDE)
                .param(IdeasController.QUERY_PARAM_SEARCH_RADIUS, VALID_SEARCH_RADIUS)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andReturn();
  }

  @Test
  public void getIdeas_MissingSearchRadius_Returns400() throws Exception {
    mockMvc
        .perform(
            get(IdeasController.GET_IDEAS_PATH)
                .param(IdeasController.QUERY_PARAM_USER_LONGITUDE, VALID_LONGITUDE)
                .param(IdeasController.QUERY_PARAM_USER_LATITUDE, VALID_LATITUDE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andReturn();
  }

  @Test
  @Ignore("Ignore until validator is implemented")
  public void getIdeas_InvalidLongitude_Returns422() throws Exception {
    mockMvc
        .perform(
            get(IdeasController.GET_IDEAS_PATH)
                .param(IdeasController.QUERY_PARAM_USER_LONGITUDE, INVALID_LONGITUDE)
                .param(IdeasController.QUERY_PARAM_USER_LATITUDE, VALID_LATITUDE)
                .param(IdeasController.QUERY_PARAM_SEARCH_RADIUS, VALID_SEARCH_RADIUS)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
        .andReturn();
  }

  @Test
  @Ignore("Ignore until validator is implemented")
  public void getIdeas_InvalidLatitude_Returns422() throws Exception {
    mockMvc
        .perform(
            get(IdeasController.GET_IDEAS_PATH)
                .param(IdeasController.QUERY_PARAM_USER_LONGITUDE, VALID_LONGITUDE)
                .param(IdeasController.QUERY_PARAM_USER_LATITUDE, INVALID_LATITUDE)
                .param(IdeasController.QUERY_PARAM_SEARCH_RADIUS, VALID_SEARCH_RADIUS)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
        .andReturn();
  }

  @Test
  @Ignore("Ignore until validator is implemented")
  public void getIdeas_InvalidSearchRadius_Returns422() throws Exception {
    mockMvc
        .perform(
            get(IdeasController.GET_IDEAS_PATH)
                .param(IdeasController.QUERY_PARAM_USER_LONGITUDE, VALID_LONGITUDE)
                .param(IdeasController.QUERY_PARAM_USER_LATITUDE, VALID_LATITUDE)
                .param(IdeasController.QUERY_PARAM_SEARCH_RADIUS, INVALID_SEARCH_RADIUS)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
        .andReturn();
  }
}
