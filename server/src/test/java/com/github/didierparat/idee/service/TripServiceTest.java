package com.github.didierparat.idee.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.didierparat.idee.TestUtil;
import com.github.didierparat.idee.model.Trip;
import com.github.didierparat.idee.provider.trip.TripProvider;
import com.github.didierparat.idee.provider.trip.model.ProviderTrip;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TripServiceTest {

  private static final String LONGITUDE = "1.0";
  private static final String LATITUDE = "1.0";
  private static final String SEARCH_RADIUS = "1.0";

  private TripService tripService;

  @Mock
  private TripProvider tripProvider;

  @Before
  public void setUp() {
    this.tripService = new TripService(tripProvider);
  }

  @Test
  public void getTripsInArea_ReturnTrips() throws Exception {
    List<ProviderTrip> providerTrips = TestUtil.readValue(
        TestUtil.RESOURCE_PROVIDER_TRIPS,
        new TypeReference<List<ProviderTrip>>() {});
    when(tripProvider.getTripsInArea(LONGITUDE, LATITUDE, SEARCH_RADIUS)).thenReturn(providerTrips);

    List<Trip> trips = tripService.getTripsInArea(LONGITUDE, LATITUDE, SEARCH_RADIUS);

    assertNotNull(trips);
    assertEquals(providerTrips.size(), trips.size());
    for (int i = 0; i < trips.size(); i++) {
      ProviderTrip providerTrip = providerTrips.get(i);
      Trip trip = trips.get(i);
      assertEquals(providerTrip.getName(), trip.getName());
      assertEquals(providerTrip.getUrl(), trip.getUrl());
      assertEquals(providerTrip.getEstimatedTime(), trip.getEstimatedTime());
      // TODO assertEquals(, trip.getLocation());
    }
  }
}
