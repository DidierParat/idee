package idee.Clients;

import java.io.IOException;
import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.logging.Logger;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by didier on 19.01.17.
 */
public class AdaptiveClient {
    private static final String RATELIMIT_REMAINING_HEADER_FIELD = "X-RateLimit-Remaining";
    private static final String RATELIMIT_RESET_HEADER_FIELD = "X-RateLimit-Reset";

    public class RateLimitExceededException extends Exception {}

    private class RateLimit {
        private Long resetTime;
        private Integer remaining;
        public void setResetTime(final Long resetTime) {
            this.resetTime = resetTime;
        }
        public void setRemaining(final Integer remaining) {
            this.remaining = remaining;
        }
        private boolean resetTimeIsPassed() {
            long now = Instant.now().toEpochMilli();
            return resetTime < now;
        }
        public boolean rateLimitExceeded() {
            if (remaining > 0) {
                return false;
            }
            if (resetTimeIsPassed()) {
                return false;
            }
            return true;
        }
    }

    private static final Logger LOGGER = Logger.getLogger(AdaptiveClient.class.getName());
    private final HashMap<String, RateLimit> rateLimitMap;
    private final Client httpClient;

    public  <T> T getData(final URI uri, Class<T> entityType)
            throws IOException, RateLimitExceededException {
        if (rateLimitExceeded(uri)) {
            LOGGER.warning("RateLimit exceeded. Could not retrieve link: " + uri);
            throw new RateLimitExceededException();
        }
        LOGGER.info("Connecting to " + uri);

        final Response response;
        try {
            response = httpClient
                    .target(uri)
                    .request(MediaType.APPLICATION_JSON)
                    .get();
        } catch (final ProcessingException e) {
            throw new IOException("Could not retrieve " + uri, e);
        }

        String rateLimitResetString = response.getHeaderString(RATELIMIT_RESET_HEADER_FIELD);
        String rateLimitRemainingString
                = response.getHeaderString(RATELIMIT_REMAINING_HEADER_FIELD);
        if (rateLimitResetString != null && rateLimitRemainingString != null) {
            Long rateLimitReset = new Long(rateLimitResetString);
            Integer rateLimitRemaining = new Integer(rateLimitRemainingString);
            updateRateLimit(uri, rateLimitReset, rateLimitRemaining);
        }

        return response.readEntity(entityType);
    }

    private void updateRateLimit(
            final URI uri,
            final Long rateLimitReset,
            final Integer rateLimitRemaining) {
        String baseUrl = uri.getHost();
        LOGGER.info("Updating rate limit for " + baseUrl + " to " + rateLimitRemaining);
        RateLimit rateLimit = rateLimitMap.get(baseUrl);
        if (rateLimit == null) {
            rateLimit = new RateLimit();
            rateLimitMap.put(baseUrl, rateLimit);
        }
        rateLimit.setResetTime(rateLimitReset);
        rateLimit.setRemaining(rateLimitRemaining);
    }

    private boolean rateLimitExceeded(final URI uri) {
        String baseUrl = uri.getHost();
        RateLimit rateLimit = rateLimitMap.get(baseUrl);
        if (rateLimit == null) {
            return false;
        }
        return rateLimit.rateLimitExceeded();
    }

    public AdaptiveClient() {
        rateLimitMap = new HashMap<>();
        httpClient = ClientBuilder.newClient();
    }
}
