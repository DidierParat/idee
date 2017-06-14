package idee.Clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import idee.Nasjonalturbase.Area;
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
    // Not working, use dev key for prod env
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

    private URI addApiKeyCreateUri(final String link) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(link);
        uriBuilder.addParameter(API_KEY_PARAM, apiKey);
        return uriBuilder.build();
    }

    public String[] getAreasIds()
            throws URISyntaxException, IOException, AdaptiveClient.RateLimitExceededException {
        // Build URI
        String link = host + "/" + OBJECT_TYPE_AREAS;
        URIBuilder uriBuilder = new URIBuilder(link);
        uriBuilder.addParameter(LIMIT_PARAM, MAX_OBJECTS_PER_REQUEST);
        uriBuilder.addParameter(API_KEY_PARAM, apiKey);
        URI initialRequest = uriBuilder.build();

        // Detect number of total areas
        String initialReply = adaptiveClient.getData(initialRequest);
        JSONObject replyJson = new JSONObject(initialReply);
        int totalNumberOfAreas = replyJson.getInt(TOTAL);
        String[] areasIds = new String[totalNumberOfAreas];

        // Compute number of missing data
        Integer numberOfRetrievedAreas = replyJson.getInt(COUNT);
        int remainingNumberAreas = totalNumberOfAreas - numberOfRetrievedAreas;
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
                + ", remaining items "
                + remainingNumberOfRequests
                + ", remaining requests "
                + remainingNumberOfRequests);

        // Parse initialReply
        int currAreaIdCount = 0;
        JSONArray areasJsonArray = replyJson.getJSONArray(DOCUMENTS);
        for (int i = 0; i < areasJsonArray.length(); ++i, ++currAreaIdCount) {
            areasIds[currAreaIdCount] = areasJsonArray.getJSONObject(i).getString(ID);
        }

        // Request and parseremaining data
        for (int requestsCounter = 0;
             requestsCounter < remainingNumberOfRequests;
             ++requestsCounter) {
            uriBuilder = new URIBuilder(link);
            uriBuilder.addParameter(SKIP_PARAM, numberOfRetrievedAreas.toString());
            uriBuilder.addParameter(LIMIT_PARAM, MAX_OBJECTS_PER_REQUEST);
            uriBuilder.addParameter(API_KEY_PARAM, apiKey);
            URI request = uriBuilder.build();
            String reply = adaptiveClient.getData(request);
            replyJson = new JSONObject(reply);
            numberOfRetrievedAreas += replyJson.getInt(COUNT);
            areasJsonArray = replyJson.getJSONArray(DOCUMENTS);
            for (int i = 0; i < areasJsonArray.length(); ++i, ++currAreaIdCount) {
                areasIds[currAreaIdCount] = areasJsonArray.getJSONObject(i).getString(ID);
            }
        }
        return areasIds;
    }

    public Area getArea(final String areaId)
            throws URISyntaxException, IOException, AdaptiveClient.RateLimitExceededException {
        // Build URI
        String link = host + "/" + OBJECT_TYPE_AREAS + "/" + areaId;
        URI uri = addApiKeyCreateUri(link);
        String areaString = adaptiveClient.getData(uri);

        // Transform json into Area object
        ObjectMapper mapper = new ObjectMapper();
        Area area = mapper.readValue(areaString, Area.class);
        return area;
    }

    // TODO handle several trips (do not forget limit per request)
    public Trip getTripPerArea(final String areaId)
            throws URISyntaxException, IOException, AdaptiveClient.RateLimitExceededException {
        // Build URI
        String link = host + "/" + OBJECT_TYPE_TUR + "/";
        URIBuilder uriBuilder = new URIBuilder(link);
        uriBuilder.addParameter(AREAS_PARAM, areaId);
        uriBuilder.addParameter(API_KEY_PARAM, apiKey);
        URI tripsInAreaRequest = uriBuilder.build();

        // Extract one trip ID from result
        String tripsInArea = adaptiveClient.getData(tripsInAreaRequest);
        final JSONObject replyJson = new JSONObject(tripsInArea);
        final JSONArray tripsInAreaJson = replyJson.getJSONArray(DOCUMENTS);
        if (tripsInAreaJson.length() == 0) {
            return null;
        }
        String tripId = tripsInAreaJson.getJSONObject(0).getString(ID);


        // Request the trip object
        link = host + "/" + OBJECT_TYPE_TUR + "/" + tripId;
        uriBuilder = new URIBuilder(link);
        uriBuilder.addParameter(API_KEY_PARAM, apiKey);
        URI tripRequest = uriBuilder.build();
        String tripString = adaptiveClient.getData(tripRequest);

        // Transform json into Trip object
        ObjectMapper mapper = new ObjectMapper();
        Trip trip = mapper.readValue(tripString, Trip.class);
        return trip;
    }
}
