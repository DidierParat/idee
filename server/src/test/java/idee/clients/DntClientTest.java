package idee.clients;

import static org.mockito.Mockito.mock;

public class DntClientTest {
    private static final String HOST = "http://test.com";
    private static final String APIKEY = "123456";
    private static final AdaptiveClient adaptiveClient = mock(AdaptiveClient.class);
    private static final DntClient dntClient = new DntClient(HOST, APIKEY);
}
