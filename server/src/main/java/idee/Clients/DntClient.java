package idee.Clients;

import idee.Nasjonalturbase.Area;
import idee.Nasjonalturbase.BaseObjects.GenericObject;
import idee.Nasjonalturbase.ListOfGenericObjects;
import idee.Nasjonalturbase.Trip;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

import static idee.Nasjonalturbase.TurbaseConstants.COUNT;
import static idee.Nasjonalturbase.TurbaseConstants.DOCUMENTS;
import static idee.Nasjonalturbase.TurbaseConstants.ID;
import static idee.Nasjonalturbase.TurbaseConstants.TOTAL;

/**
 * Created by didier on 27.01.17.
 */
public class DntClient {
    private static final String OBJECT_TYPE_AREAS = "omr%C3%A5der";
    private static final String OBJECT_TYPE_TUR = "turer";
    private static final String MAX_OBJECTS_PER_REQUEST = "50";
    private static final int MAX_OBJECTS_PER_REQUEST_INT = Integer.valueOf(MAX_OBJECTS_PER_REQUEST);
    private static final String LIMIT_PARAM = "limit";
    private static final String SKIP_PARAM = "skip";
    private static final String AREAS_PARAM = "områder[]";
    private static final String API_KEY_PARAM = "api_key";
    private static final Logger LOGGER = Logger.getLogger(DntClient.class.getName());
    private final String host;
    private final String apiKey;
    private final AdaptiveClient adaptiveClient;

    public DntClient(final String host, final String apiKey, final AdaptiveClient adaptiveClient) {
        this.adaptiveClient = adaptiveClient;
        this.host = host;
        this.apiKey = apiKey;
    }

    public String[] getAreasIds()
            throws URISyntaxException, IOException, AdaptiveClient.RateLimitExceededException {
        final URI requestFirstBatchOfAreas = buildAreasRequestUri(0);
        final ListOfGenericObjects firstBatchOfAreas
                = adaptiveClient.getData(requestFirstBatchOfAreas, ListOfGenericObjects.class);
        final int totalNumberOfAreas = firstBatchOfAreas.getTotal();
        final String[] areasIds = new String[totalNumberOfAreas];

        Integer numberOfRetrievedAreas = firstBatchOfAreas.getCount();
        final int remainingNumberAreas = totalNumberOfAreas - numberOfRetrievedAreas;
        int remainingNumberOfRequests
                = remainingNumberAreas / MAX_OBJECTS_PER_REQUEST_INT;
        if (remainingNumberAreas % MAX_OBJECTS_PER_REQUEST_INT != 0) {
            remainingNumberOfRequests++;
        }
        LOGGER.info(
                "Retrieving areas IDs: got "
                + numberOfRetrievedAreas
                + " items, total "
                + totalNumberOfAreas
                + ", remaining requests "
                + remainingNumberOfRequests);

        GenericObject[] areasIdArray = firstBatchOfAreas.getDocuments();
        for (int i = 0; i < areasIdArray.length; ++i) {
            areasIds[i] = areasIdArray[i].getId();
        }

        for (int requestsCounter = 0;
             requestsCounter < remainingNumberOfRequests;
             ++requestsCounter) {
            final URI requestNextBatch = buildAreasRequestUri(numberOfRetrievedAreas);
            final ListOfGenericObjects nextBatchOfAreas
                    = adaptiveClient.getData(requestNextBatch, ListOfGenericObjects.class);
            areasIdArray = nextBatchOfAreas.getDocuments();
            for (int i = numberOfRetrievedAreas; i < areasIdArray.length; ++i) {
                areasIds[i] = areasIdArray[i].getId();
            }
            numberOfRetrievedAreas += nextBatchOfAreas.getCount();
        }
        return areasIds;
    }

    public Area getArea(final String areaId)
            throws URISyntaxException, IOException, AdaptiveClient.RateLimitExceededException {
        final String link = host + "/" + OBJECT_TYPE_AREAS + "/" + areaId;
        final URI uri = createBaseRequestUriBuilder(link, 0).build();
        return adaptiveClient.getData(uri, Area.class);
    }

    // TODO handle several trips (do not forget limit per request)
    public Trip getTripPerArea(final String areaId)
            throws URISyntaxException, IOException, AdaptiveClient.RateLimitExceededException {
        final URI tripsInAreaRequest = buildTripsInAreaRequestUri(areaId, 0);

        final JSONObject replyJson
                = new JSONObject(adaptiveClient.getData(tripsInAreaRequest, String.class));
        final JSONArray tripsInAreaJson = replyJson.getJSONArray(DOCUMENTS);
        if (tripsInAreaJson.length() == 0) {
            return null;
        }
        final String tripId = tripsInAreaJson.getJSONObject(0).getString(ID);
        return getTrip(tripId);
    }

    private URIBuilder createBaseRequestUriBuilder(
            final String endpoint, final Integer numberOfObjectsToSkip) throws URISyntaxException {
        final URIBuilder uriBuilder = new URIBuilder(endpoint);
        if (numberOfObjectsToSkip == 0) {
            uriBuilder.addParameter(SKIP_PARAM, numberOfObjectsToSkip.toString());
        }
        uriBuilder.addParameter(LIMIT_PARAM, MAX_OBJECTS_PER_REQUEST);
        uriBuilder.addParameter(API_KEY_PARAM, apiKey);
        return uriBuilder;
    }

    private URI buildAreasRequestUri(
            final Integer numberOfObjectsToSkip) throws URISyntaxException {
        final String areaEndpoint = host + "/" + OBJECT_TYPE_AREAS;
        return createBaseRequestUriBuilder(areaEndpoint, numberOfObjectsToSkip).build();
    }

    private URI buildTripsInAreaRequestUri(
            final String areaId, final Integer numberOfObjectsToSkip) throws URISyntaxException {
        final String tripEndpoint = host + "/" + OBJECT_TYPE_TUR;
        return createBaseRequestUriBuilder(tripEndpoint, numberOfObjectsToSkip)
                .addParameter(AREAS_PARAM, areaId)
                .build();
    }

    private Trip getTrip(final String tripId)
            throws URISyntaxException, IOException, AdaptiveClient.RateLimitExceededException {
        final String tripEndpoint = host + "/" + OBJECT_TYPE_TUR + "/" + tripId;
        final URI tripRequest = createBaseRequestUriBuilder(tripEndpoint, 0).build();
        return adaptiveClient.getData(tripRequest, Trip.class);
    }
}
