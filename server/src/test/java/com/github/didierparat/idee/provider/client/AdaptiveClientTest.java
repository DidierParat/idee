package com.github.didierparat.idee.provider.client;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RunWith(MockitoJUnitRunner.class)
public class AdaptiveClientTest {

  private static final long ONE_HOUR_IN_MS = 3600000;

  private AdaptiveClient adaptiveClient;
  @Mock
  private RestTemplate restTemplate;

  @Before
  public void setUp() {
    this.adaptiveClient = new AdaptiveClient(restTemplate);
  }

  @Test
  public void getData_NotRateLimited_ReturnsResponse() throws Exception {
    URI uri = new URI("http://localhost:8080/");
    String body = "{}";
    long currentTimePlusOneHour = System.currentTimeMillis() + ONE_HOUR_IN_MS;
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add(AdaptiveClient.RATE_LIMIT_REMAINING_HEADER_FIELD, "10");
    httpHeaders.add(
        AdaptiveClient.RATE_LIMIT_RESET_HEADER_FIELD, Long.toString(currentTimePlusOneHour));
    ResponseEntity<String> responseEntity = new ResponseEntity<String>(
        body, httpHeaders, HttpStatus.OK);
    when(restTemplate.exchange(
            eq(uri), eq(HttpMethod.GET), any(), ArgumentMatchers.<Class<String>>any()))
        .thenReturn(responseEntity);

    String responseBody = adaptiveClient.getData(uri, String.class);

    assertEquals(body, responseBody);
  }

  @Test(expected = ClientException.class)
  public void getData_RateLimited_ThrowsClientException() throws Exception {
    URI uri = new URI("http://localhost:8080/");
    String body = "{}";
    long currentTimePlusOneHour = System.currentTimeMillis() + ONE_HOUR_IN_MS;
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add(AdaptiveClient.RATE_LIMIT_REMAINING_HEADER_FIELD, "0");
    httpHeaders.add(
        AdaptiveClient.RATE_LIMIT_RESET_HEADER_FIELD, Long.toString(currentTimePlusOneHour));
    ResponseEntity<String> responseEntity = new ResponseEntity<String>(
        body, httpHeaders, HttpStatus.OK);
    when(restTemplate.exchange(
        eq(uri), eq(HttpMethod.GET), any(), ArgumentMatchers.<Class<String>>any()))
        .thenReturn(responseEntity);

    adaptiveClient.getData(uri, String.class);
    adaptiveClient.getData(uri, String.class);
  }
}
