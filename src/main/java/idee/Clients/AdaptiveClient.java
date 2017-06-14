package idee.Clients;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.time.Instant;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * Created by didier on 19.01.17.
 */
public class AdaptiveClient {
    private static final String RATELIMIT_REMAINING_HEADER_FIELD = "X-RateLimit-Remaining";
    private static final String RATELIMIT_RESET_HEADER_FIELD = "X-RateLimit-Reset";
    private static final String USER_AGENT_REQUEST_PROPERTY = "User-Agent";
    private static final String USER_AGENT_REQUEST_PROPERTY_VALUE = "java";

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

    public String getData(final URI uri) throws IOException, RateLimitExceededException {
        if (rateLimitExceeded(uri)) {
            LOGGER.warning("RateLimit exceeded. Could not retrieve link: " + uri);
            throw new RateLimitExceededException();
        }

        URL url = uri.toURL();
        LOGGER.info("Connecting to " + url);

        URLConnection connection = url.openConnection();
        connection.setRequestProperty(
                USER_AGENT_REQUEST_PROPERTY,
                USER_AGENT_REQUEST_PROPERTY_VALUE);


        // Update ratelimit for this API
        String rateLimitResetString = connection.getHeaderField(RATELIMIT_RESET_HEADER_FIELD);
        String rateLimitRemainingString =
                connection.getHeaderField(RATELIMIT_REMAINING_HEADER_FIELD);
        if (rateLimitResetString != null && rateLimitRemainingString != null) {
            Long rateLimitReset = new Long(rateLimitResetString);
            Integer rateLimitRemaining = new Integer(rateLimitRemainingString);
            updateRateLimit(uri, rateLimitReset, rateLimitRemaining);
        }

        // Create string from buffer
        InputStream in = new BufferedInputStream(connection.getInputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        StringBuilder result = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
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
    }
}
