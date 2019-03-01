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

@Slf4j
@Service
public class DntLocationProvider {

  private static final String SKIP_PARAM = "skip";
  private static final int MAX_OBJECTS_PER_REQUEST_INT = Integer.valueOf(
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
      log.error("Could not populate DntLocationProvider from DNT. Caught exception: " + exception);
    }
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

  public List<Area> getAreas() {
    return areas;
  }
}
