package com.github.didierparat.idee.provider.trip.dnt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.didierparat.idee.TestUtil;
import com.github.didierparat.idee.provider.client.AdaptiveClient;
import com.github.didierparat.idee.provider.common.dnt.ListOfGenericObjects;
import com.github.didierparat.idee.provider.trip.dnt.location.DntLocationProvider;
import com.github.didierparat.idee.provider.trip.dnt.location.model.Area;
import com.github.didierparat.idee.provider.trip.dnt.model.DntTrip;
import com.github.didierparat.idee.provider.trip.model.ProviderTrip;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.URI;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class DntTripProviderTest {

  private static final String LONGITUDE = "1.0";
  private static final String LATITUDE = "1.0";
  private static final String SEARCH_RADIUS = "50";
  private static final String HOST = "https://dev.nasjonalturbase.no";
  private static final String API_KEY = "123";

  private DntTripProvider dntTripProvider;
  @Mock
  private DntLocationProvider dntLocationProvider;
  @Mock
  private AdaptiveClient adaptiveClient;

  @Before
  public void setUp() {
    this.dntTripProvider = new DntTripProvider(HOST, API_KEY, adaptiveClient, dntLocationProvider);
  }

  @Test
  public void getTripsInArea() throws Exception {
    List<Area> areas = TestUtil.readValue(
        TestUtil.RESOURCE_DNT_PROVIDER_AREAS, new TypeReference<List<Area>>() {});
    when(dntLocationProvider.getAreasInRange(eq(LONGITUDE), eq(LATITUDE), eq(SEARCH_RADIUS)))
        .thenReturn(areas);
    String areaId = areas.get(0).getId();
    ListOfGenericObjects listOfGenericObjects = TestUtil.readValue(
        TestUtil.RESOURCE_DNT_PROVIDER_TRIPS_ID, ListOfGenericObjects.class);
    URI tripsIdsUri = new URI(
        String.format("%s/turer/?områder[]=%s&limit=50&api_key=123", HOST, areaId));
    when(adaptiveClient.getData(eq(tripsIdsUri), eq(ListOfGenericObjects.class)))
        .thenReturn(listOfGenericObjects);
    String tripId = listOfGenericObjects.getDocuments()[0].getId();
    URI tripUri = new URI(String.format("%s/turer/%s?limit=50&api_key=123", HOST, tripId));
    DntTrip dntTrip = TestUtil.readValue(TestUtil.RESOURCE_DNT_PROVIDER_TRIP, DntTrip.class);
    when(adaptiveClient.getData(eq(tripUri), eq(DntTrip.class))).thenReturn(dntTrip);

    List<ProviderTrip> trips = dntTripProvider.getTripsInArea(LONGITUDE, LATITUDE, SEARCH_RADIUS);

    assertNotNull(trips);
    assertEquals(1, trips.size());
  }
}
