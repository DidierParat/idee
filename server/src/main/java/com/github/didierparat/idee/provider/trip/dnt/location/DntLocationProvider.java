package com.github.didierparat.idee.provider.trip.dnt.location;

import com.github.didierparat.idee.provider.client.AdaptiveClient;
import com.github.didierparat.idee.provider.client.ClientException;
import com.github.didierparat.idee.provider.common.dnt.DntConstants;
import com.github.didierparat.idee.provider.common.dnt.ListOfGenericObjects;
import com.github.didierparat.idee.provider.common.dnt.baseobjects.GenericObject;
import com.github.didierparat.idee.provider.trip.dnt.location.model.Area;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DntLocationProvider {

  private static final String SKIP_PARAM = "skip";
  private static final Integer MAX_OBJECTS_PER_REQUEST_INT = Integer.valueOf(
      DntConstants.MAX_OBJECTS_PER_REQUEST);

  private final List<Area> areas;
  private final String host;
  private final String apiKey;
  private final AdaptiveClient adaptiveClient;

  @Autowired
  public DntLocationProvider(
      @Value("${dnt.host}") final String host,
      @Value("${dnt.apikey}") final String apiKey,
      final AdaptiveClient adaptiveClient) {
    this.host = host;
    this.apiKey = apiKey;
    this.adaptiveClient = adaptiveClient;
    // save areas from DNT to avoid spamming during each request
    this.areas = new ArrayList<>();
    try {
      final String[] areasIds = getAreasIds();
      for(String areaId : areasIds) {
        final Area area = getArea(areaId);
        areas.add(area);
      }
    } catch (ClientException exception) {
      log.error("Could not populate DntLocationProvider from DNT.", exception);
    }
  }

  public List<Area> getAreasInRange(
      final String longitudeString, final String latitudeString, final String searchRadiusString) {

    final Double longitude = Double.valueOf(longitudeString);
    final Double latitudeA = Double.valueOf(latitudeString);
    final Integer searchRadius = Integer.valueOf(searchRadiusString);
    return areas
        .stream()
        .filter(area -> areaIsInSearchRadius(longitude, latitudeA, searchRadius, area))
        .collect(Collectors.toList());
  }

  private Area getArea(final String areaId) throws ClientException {
    final URI uri
        = UriComponentsBuilder
        .fromUriString(host)
        .path(DntConstants.OBJECT_TYPE_AREAS)
        .path(areaId)
        .queryParam(DntConstants.QUERY_PARAM_LIMIT, DntConstants.MAX_OBJECTS_PER_REQUEST)
        .queryParam(DntConstants.QUERY_PARAM_API_KEY, apiKey)
        .build()
        .toUri();
    return adaptiveClient.getData(uri, Area.class);
  }

  private String[] getAreasIds() throws ClientException {
    final URI initialAreasRequest
        = UriComponentsBuilder
        .fromUriString(host)
        .path(DntConstants.OBJECT_TYPE_AREAS)
        .queryParam(SKIP_PARAM, "0")
        .build()
        .toUri();
    final ListOfGenericObjects firstBatch
        = adaptiveClient.getData(initialAreasRequest, ListOfGenericObjects.class);
    final int totalNumberOfObjects = firstBatch.getTotal();
    final String[] objectIds = new String[totalNumberOfObjects];
    final GenericObject[] firstBatchObjectsArray = firstBatch.getDocuments();
    int currentObjectIdsStored = 0;
    for (int i = 0;
        i < firstBatchObjectsArray.length;
        ++i, ++currentObjectIdsStored) {
      objectIds[currentObjectIdsStored] = firstBatchObjectsArray[i].getId();
    }

    Integer numberOfRetrievedObjects = firstBatch.getCount();
    final int remainingNumberAreas = totalNumberOfObjects - numberOfRetrievedObjects;
    int remainingNumberOfRequests
        = remainingNumberAreas / MAX_OBJECTS_PER_REQUEST_INT;
    if (remainingNumberAreas % MAX_OBJECTS_PER_REQUEST_INT != 0) {
      remainingNumberOfRequests++;
    }
    log.debug(
        "Retrieving object IDs: got "
            + totalNumberOfObjects
            + " items, total "
            + numberOfRetrievedObjects
            + ", remaining requests "
            + remainingNumberOfRequests);

    for (int requestsCounter = 0;
        requestsCounter < remainingNumberOfRequests;
        ++requestsCounter) {
      final URI areasRequest
          = UriComponentsBuilder
          .fromUriString(host)
          .path(DntConstants.OBJECT_TYPE_AREAS)
          .queryParam(SKIP_PARAM, numberOfRetrievedObjects)
          .build()
          .toUri();
      final ListOfGenericObjects nextBatchOfAreas
          = adaptiveClient.getData(areasRequest, ListOfGenericObjects.class);
      final GenericObject[] genericObjectList = nextBatchOfAreas.getDocuments();
      for (int i = 0;
          i < genericObjectList.length;
          ++i, ++currentObjectIdsStored) {
        objectIds[currentObjectIdsStored] = genericObjectList[i].getId();
      }
      numberOfRetrievedObjects += nextBatchOfAreas.getCount();
    }
    return objectIds;
  }

  private Integer distanceInKm(
      final Double longitudeA,
      final Double latitudeA,
      final Double longitudeB,
      final Double latitudeB) {

    Integer earthRadiusKm = 6371;
    Double deltaLatitude = Math.toRadians(latitudeB - latitudeA);
    Double deltaLongitude = Math.toRadians(longitudeB - longitudeA);
    Double haversineA = Math.sin(deltaLatitude / 2) * Math.sin(deltaLatitude / 2)
        + Math.cos(Math.toRadians(latitudeA))
        * Math.cos(Math.toRadians(latitudeB))
        * Math.sin(deltaLongitude / 2)
        * Math.sin(deltaLongitude / 2);
    Double haversineC = 2 * Math.atan2(Math.sqrt(haversineA), Math.sqrt(1 - haversineA));
    Double dist = (earthRadiusKm * haversineC);
    return dist.intValue();
  }

  private boolean areaIsInSearchRadius(
      final Double longitude,
      final Double latitude,
      final Integer searchRadius,
      final Area area) {
    Integer distance = distanceInKm(
        longitude, latitude, area.getCenter().getLongitude(), area.getCenter().getLatitude());
    return distance < searchRadius;
  }
}
