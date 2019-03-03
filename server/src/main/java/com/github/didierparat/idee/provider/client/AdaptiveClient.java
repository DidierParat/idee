package com.github.didierparat.idee.provider.client;

import com.google.common.annotations.VisibleForTesting;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.Instant;

@Slf4j
@Service
public class AdaptiveClient {

  @VisibleForTesting
  static final String RATE_LIMIT_REMAINING_HEADER_FIELD = "X-RateLimit-Remaining";
  @VisibleForTesting
  static final String RATE_LIMIT_RESET_HEADER_FIELD = "X-RateLimit-Reset";

  private final RateLimit rateLimit;
  private final RestTemplate restTemplate;

  @Autowired
  public AdaptiveClient(final RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
    this.rateLimit = new RateLimit();
  }

  public <T> T getData(final URI uri, final Class<T> responseType) throws ClientException {
    if (rateLimitExceeded()) {
      throw new ClientException("RateLimit exceeded.");
    }

    ResponseEntity<T> responseEntity
        = restTemplate.exchange(uri, HttpMethod.GET, null, responseType);

    HttpHeaders responseHeaders = responseEntity.getHeaders();
    String rateLimitResetString = responseHeaders.getFirst(RATE_LIMIT_RESET_HEADER_FIELD);
    String rateLimitRemainingString
        = responseHeaders.getFirst(RATE_LIMIT_REMAINING_HEADER_FIELD);
    if (rateLimitResetString != null && rateLimitRemainingString != null) {
      Long rateLimitReset = new Long(rateLimitResetString);
      Integer rateLimitRemaining = new Integer(rateLimitRemainingString);
      updateRateLimit(uri, rateLimitReset, rateLimitRemaining);
    }

    return responseEntity.getBody();
  }

  private void updateRateLimit(
      final URI uri,
      final Long rateLimitReset,
      final Integer rateLimitRemaining) {
    String baseUrl = uri.getHost();
    log.debug("Updating rate limit for {} to {}.", baseUrl, rateLimitRemaining);
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

    void setResetTime(final Long resetTime) {
      this.resetTime = resetTime;
    }

    void setRemaining(final Integer remaining) {
      this.remaining = remaining;
    }

    private boolean resetTimeIsPassed() {
      final long now = Instant.now().toEpochMilli();
      return resetTime < now;
    }

    boolean rateLimitExceeded() {
      if (remaining > 0) {
        return false;
      }
      return !resetTimeIsPassed();
    }
  }
}
