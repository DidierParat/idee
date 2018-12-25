package idee.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Slf4j
public class AdaptiveClient {

  private static final String RATELIMIT_REMAINING_HEADER_FIELD = "X-RateLimit-Remaining";
  private static final String RATELIMIT_RESET_HEADER_FIELD = "X-RateLimit-Reset";

  private final RateLimit rateLimit;
  private final Client httpClient;

  public AdaptiveClient() {
    this.httpClient = ClientBuilder.newClient();
    this.rateLimit = new RateLimit();
  }

  public <T> T getData(final URIBuilder uriBuilder, Class<T> entityType)
      throws ClientException {
    if (rateLimitExceeded()) {
      throw new ClientException("RateLimit exceeded.");
    }
    final URI uri;
    try {
      uri = uriBuilder.build();
    } catch (URISyntaxException exception) {
      throw new ClientException("Could not build the provided URIBuilder.", exception);
    }
    log.debug("Connecting to " + uri);

    final Response response;
    try {
      response = httpClient
          .target(uri)
          .request(MediaType.APPLICATION_JSON)
          .get();
    } catch (final ProcessingException exception) {
      throw new ClientException("Could not retrieve " + uri, exception);
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
    log.debug("Updating rate limit for " + baseUrl + " to " + rateLimitRemaining);
    rateLimit.setResetTime(rateLimitReset);
    rateLimit.setRemaining(rateLimitRemaining);
  }

  private boolean rateLimitExceeded() {
    return rateLimit.rateLimitExceeded();
  }

  private class RateLimit {

    private Long resetTime;
    private Integer remaining;

    RateLimit() {
      this.remaining = Integer.MAX_VALUE;
    }

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
}
