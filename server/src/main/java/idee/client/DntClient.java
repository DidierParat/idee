package idee.client;

import static idee.model.nasjonalturbase.TurbaseConstants.DOCUMENTS;
import static idee.model.nasjonalturbase.TurbaseConstants.ID;

import idee.model.nasjonalturbase.Area;
import idee.model.nasjonalturbase.ListOfGenericObjects;
import idee.model.nasjonalturbase.Trip;
import idee.model.nasjonalturbase.baseobjects.GenericObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URISyntaxException;

@Slf4j
public class DntClient {

  private static final String OBJECT_TYPE_AREAS = "omr%C3%A5der";
  private static final String OBJECT_TYPE_TUR = "turer";
  private static final String MAX_OBJECTS_PER_REQUEST = "50";
  private static final int MAX_OBJECTS_PER_REQUEST_INT = Integer.valueOf(MAX_OBJECTS_PER_REQUEST);
  private static final String LIMIT_PARAM = "limit";
  private static final String SKIP_PARAM = "skip";
  private static final String AREAS_PARAM = "områder[]";
  private static final String API_KEY_PARAM = "api_key";
  private final String host;
  private final String apiKey;
  private final AdaptiveClient adaptiveClient;

  public DntClient(final String host, final String apiKey) {
    this.adaptiveClient = new AdaptiveClient();
    this.host = host;
    this.apiKey = apiKey;
  }

  public String[] getAreasIds() throws ClientException {
    final URIBuilder areasRequest = buildAreasRequest();
    return getAllIds(areasRequest);
  }

  private String[] getAllIds(final URIBuilder request) throws ClientException {
    request.addParameter(SKIP_PARAM, "0");
    final ListOfGenericObjects firstBatch
        = adaptiveClient.getData(request, ListOfGenericObjects.class);
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
      request.setParameter(SKIP_PARAM, numberOfRetrievedObjects.toString());
      final ListOfGenericObjects nextBatchOfAreas
          = adaptiveClient.getData(request, ListOfGenericObjects.class);
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

  public Area getArea(final String areaId) throws ClientException {
    final String link = host + "/" + OBJECT_TYPE_AREAS + "/" + areaId;
    final URIBuilder uriBuilder = createBaseRequestUriBuilder(link);
    return adaptiveClient.getData(uriBuilder, Area.class);
  }

  // TODO handle several trips (do not forget limit per request)
  public Trip getTripPerArea(final String areaId) throws ClientException {
    final URIBuilder tripsInAreaRequest = buildTripsInAreaRequestUri(areaId);

    final JSONObject replyJson
        = new JSONObject(adaptiveClient.getData(tripsInAreaRequest, String.class));
    final JSONArray tripsInAreaJson = replyJson.getJSONArray(DOCUMENTS);
    if (tripsInAreaJson.length() == 0) {
      return null;
    }
    final String tripId = tripsInAreaJson.getJSONObject(0).getString(ID);
    return getTrip(tripId);
  }

  private URIBuilder createBaseRequestUriBuilder(final String endpoint) throws ClientException {
    final URIBuilder uriBuilder;
    try {
      uriBuilder = new URIBuilder(endpoint);
    } catch (URISyntaxException exception) {
      throw new ClientException(
          "Could not create base request for endpoint: " + endpoint,
          exception);
    }
    uriBuilder.addParameter(LIMIT_PARAM, MAX_OBJECTS_PER_REQUEST);
    uriBuilder.addParameter(API_KEY_PARAM, apiKey);
    return uriBuilder;
  }

  private URIBuilder buildAreasRequest() throws ClientException {
    final String areaEndpoint = host + "/" + OBJECT_TYPE_AREAS;
    return createBaseRequestUriBuilder(areaEndpoint);
  }

  private URIBuilder buildTripsInAreaRequestUri(final String areaId) throws ClientException {
    final String tripEndpoint = host + "/" + OBJECT_TYPE_TUR;
    return createBaseRequestUriBuilder(tripEndpoint)
        .addParameter(AREAS_PARAM, areaId);
  }

  private Trip getTrip(final String tripId) throws ClientException {
    final String tripEndpoint = host + "/" + OBJECT_TYPE_TUR + "/" + tripId;
    final URIBuilder tripRequest = createBaseRequestUriBuilder(tripEndpoint);
    return adaptiveClient.getData(tripRequest, Trip.class);
  }
}
