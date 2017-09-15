package idee.Clients;

import idee.Nasjonalturbase.Area;
import idee.Nasjonalturbase.BaseObjects.GenericObject;
import idee.Nasjonalturbase.ListOfGenericObjects;
import idee.Nasjonalturbase.Trip;
import java.net.URISyntaxException;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.logging.Logger;

import static idee.Nasjonalturbase.TurbaseConstants.DOCUMENTS;
import static idee.Nasjonalturbase.TurbaseConstants.ID;

public class DntClient extends AdaptiveClient {
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

    public DntClient(final String host, final String apiKey) {
        this.host = host;
        this.apiKey = apiKey;
    }

    public String[] getAreasIds()
            throws ClientException {
        final URIBuilder areasRequest = buildAreasRequest();
        return getAllIds(areasRequest);
    }

    private String[] getAllIds(final URIBuilder request)
            throws ClientException {
        request.addParameter(SKIP_PARAM, "0");
        final ListOfGenericObjects firstBatch
                = getData(request, ListOfGenericObjects.class);
        final int totalNumberOfObjects = firstBatch.getTotal();
        final String[] objectIds = new String[totalNumberOfObjects];

        Integer numberOfRetrievedObjects = firstBatch.getCount();
        final int remainingNumberAreas = totalNumberOfObjects - numberOfRetrievedObjects;
        int remainingNumberOfRequests
                = remainingNumberAreas / MAX_OBJECTS_PER_REQUEST_INT;
        if (remainingNumberAreas % MAX_OBJECTS_PER_REQUEST_INT != 0) {
            remainingNumberOfRequests++;
        }
        LOGGER.info(
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
                    = getData(request, ListOfGenericObjects.class);
            final GenericObject[] genericObjectList = nextBatchOfAreas.getDocuments();
            for (int i = nextBatchOfAreas.getCount(); i < genericObjectList.length; ++i) {
                objectIds[i] = genericObjectList[i].getId();
            }
            numberOfRetrievedObjects += nextBatchOfAreas.getCount();
        }
        return objectIds;
    }

    private void extractIdsFromList(
            final String[] objectIds, final ListOfGenericObjects listOfGenericObjects) {
        final GenericObject[] objectIdArray = listOfGenericObjects.getDocuments();
        for (int i = 0; i < objectIdArray.length; ++i) {
            objectIds[i] = objectIdArray[i].getId();
        }
    }

    public Area getArea(final String areaId)
            throws ClientException {
        final String link = host + "/" + OBJECT_TYPE_AREAS + "/" + areaId;
        final URIBuilder uriBuilder = createBaseRequestUriBuilder(link);
        return getData(uriBuilder, Area.class);
    }

    // TODO handle several trips (do not forget limit per request)
    public Trip getTripPerArea(final String areaId)
            throws ClientException {
        final URIBuilder tripsInAreaRequest = buildTripsInAreaRequestUri(areaId);

        final JSONObject replyJson
                = new JSONObject(getData(tripsInAreaRequest, String.class));
        final JSONArray tripsInAreaJson = replyJson.getJSONArray(DOCUMENTS);
        if (tripsInAreaJson.length() == 0) {
            return null;
        }
        final String tripId = tripsInAreaJson.getJSONObject(0).getString(ID);
        return getTrip(tripId);
    }

    private URIBuilder createBaseRequestUriBuilder(
            final String endpoint) throws ClientException {
        final URIBuilder uriBuilder;
        try {
            uriBuilder = new URIBuilder(endpoint);
        } catch (URISyntaxException e) {
            throw new ClientException("Could not create base request for endpoint: " + endpoint, e);
        }
        uriBuilder.addParameter(LIMIT_PARAM, MAX_OBJECTS_PER_REQUEST);
        uriBuilder.addParameter(API_KEY_PARAM, apiKey);
        return uriBuilder;
    }

    private URIBuilder buildAreasRequest() throws ClientException {
        final String areaEndpoint = host + "/" + OBJECT_TYPE_AREAS;
        return createBaseRequestUriBuilder(areaEndpoint);
    }

    private URIBuilder buildTripsInAreaRequestUri(
            final String areaId) throws ClientException {
        final String tripEndpoint = host + "/" + OBJECT_TYPE_TUR;
        return createBaseRequestUriBuilder(tripEndpoint)
                .addParameter(AREAS_PARAM, areaId);
    }

    private Trip getTrip(final String tripId)
            throws ClientException {
        final String tripEndpoint = host + "/" + OBJECT_TYPE_TUR + "/" + tripId;
        final URIBuilder tripRequest = createBaseRequestUriBuilder(tripEndpoint);
        return getData(tripRequest, Trip.class);
    }
}
