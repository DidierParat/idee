package com.github.didierparat.idee.provider.trip.dnt;

import static com.github.didierparat.idee.provider.common.dnt.DntConstants.MAX_OBJECTS_PER_REQUEST;

import com.github.didierparat.idee.provider.ProviderException;
import com.github.didierparat.idee.provider.client.AdaptiveClient;
import com.github.didierparat.idee.provider.client.ClientException;
import com.github.didierparat.idee.provider.common.dnt.DntConstants;
import com.github.didierparat.idee.provider.common.dnt.ListOfGenericObjects;
import com.github.didierparat.idee.provider.common.dnt.baseobjects.EstimatedTime;
import com.github.didierparat.idee.provider.common.dnt.baseobjects.GenericObject;
import com.github.didierparat.idee.provider.trip.TripProvider;
import com.github.didierparat.idee.provider.trip.dnt.location.DntLocationProvider;
import com.github.didierparat.idee.provider.trip.dnt.location.model.Area;
import com.github.didierparat.idee.provider.trip.dnt.model.DntTrip;
import com.github.didierparat.idee.provider.trip.model.ProviderTrip;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DntTripProvider implements TripProvider {

  private final String host;
  private final String apiKey;
  private final AdaptiveClient adaptiveClient;
  private final DntLocationProvider dntLocationProvider;

  @Autowired
  public DntTripProvider(
      @Value("${dnt.host}") final String host,
      @Value("${dnt.apikey}") final String apiKey,
      final AdaptiveClient adaptiveClient,
      final DntLocationProvider dntLocationProvider) {
    this.host = host;
    this.apiKey = apiKey;
    this.adaptiveClient = adaptiveClient;
    this.dntLocationProvider = dntLocationProvider;
  }

  // TODO handle several trips (do not forget limit per request)
  @Override
  public List<ProviderTrip> getTripsInArea(
      final String longitude, final String latitude, final String searchRadiusKm) {
    List<Area> areas = dntLocationProvider.getAreas();
    if (areas.isEmpty()) {
      log.debug("No areas retrieved from DNT.");
      return Collections.emptyList();
    }
    String areaId = areas.get(0).getId();
    try {
      final List<String> tripsIds = getTripsIdsInArea(areaId);
      if (tripsIds.isEmpty()) {
        log.debug("No trip found for area ID: {}.", areaId);
        return Collections.emptyList();
      }
      DntTrip dntTrip = getTrip(tripsIds.get(0));
      return Collections.singletonList(convertToTrip(dntTrip));
    } catch (ClientException exception) {
      throw new ProviderException(
          "Exception thrown by AdaptiveClient while retrieving trips", exception);
    }
  }

  private List<String> getTripsIdsInArea(final String areaId) throws ClientException {
    final URI tripsIdsInAreaUri
        = UriComponentsBuilder
        .fromUriString(host)
        .path(DntConstants.OBJECT_TYPE_TUR)
        .queryParam(DntConstants.QUERY_PARAM_AREAS, areaId)
        .queryParam(DntConstants.QUERY_PARAM_LIMIT, MAX_OBJECTS_PER_REQUEST)
        .queryParam(DntConstants.QUERY_PARAM_API_KEY, apiKey)
        .build()
        .toUri();

    ListOfGenericObjects listOfGenericObjects
        = adaptiveClient.getData(tripsIdsInAreaUri, ListOfGenericObjects.class);

    return Arrays.stream(listOfGenericObjects.getDocuments())
        .map(GenericObject::getId)
        .collect(Collectors.toList());
  }

  private DntTrip getTrip(final String tripId) throws ClientException {
    final URI tripUri
        = UriComponentsBuilder
        .fromUriString(host)
        .path(DntConstants.OBJECT_TYPE_TUR)
        .path(tripId)
        .queryParam(DntConstants.QUERY_PARAM_LIMIT, MAX_OBJECTS_PER_REQUEST)
        .queryParam(DntConstants.QUERY_PARAM_API_KEY, apiKey)
        .build()
        .toUri();
    return adaptiveClient.getData(tripUri, DntTrip.class);
  }

  private ProviderTrip convertToTrip(final DntTrip dntTrip) {
    return new ProviderTrip(
        dntTrip.getName(),
        dntTrip.getDescription(),
        dntTrip.getDistance(),
        dntTrip.getDirection(),
        dntTrip.getMunicipalities(),
        dntTrip.getCounties(),
        dntTrip.getAccess(),
        dntTrip.getGrading(),
        dntTrip.getSuitableFor(),
        dntTrip.getHandicap(),
        dntTrip.getSeasons(),
        formatEstimatedTime(dntTrip.getTimeSpent().getNormal()),
        dntTrip.getUrl());
  }

  private String formatEstimatedTime(final EstimatedTime estimatedTime) {
    return String.format(
        "%s days, %sh%smin",
        estimatedTime.getDays(),
        estimatedTime.getHours(),
        estimatedTime.getMinutes());
  }
}
