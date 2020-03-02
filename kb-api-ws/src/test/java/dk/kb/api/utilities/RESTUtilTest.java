package dk.kb.api.utilities;

import dk.kb.api.errors.error400.BadSolRequestException;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.mockserver.model.Header;
import org.mockserver.model.HttpError;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

import java.util.LinkedList;
import java.util.List;


@ExtendWith({MockitoExtension.class, MockServerExtension.class})
@MockServerSettings(ports = {8787, 8888})
public class RESTUtilTest {
    public static final String MOCK_SERVER_PATH = "/solr/ds/admin/ping";
    public static final String API_PATH = "/ds/admin/ping";
    private ClientAndServer mockServer;

    @InjectMocks
    static RESTUtil restUtil;

    @BeforeAll
    public static void setup(){
        String  solrUrl = "http://localhost:8888/solr/";
        restUtil = new RESTUtil(solrUrl);
    }

    @BeforeEach
    public void startServer(ClientAndServer client) {
        mockServer = client;
        createDefaultExpectations(mockServer);
    }

    private void createDefaultExpectations(ClientAndServer mockServer) {
        mockServer.reset();
        Header header = new Header("Accept", "application/xml");
        mockServer.when(HttpRequest.request()
                .withMethod("GET")
                .withPath(MOCK_SERVER_PATH)
                .withHeaders(header))
                .respond(HttpResponse.response()
                        .withStatusCode(200)
                        .withBody("OK")
                );
    }

    private static void createExpectationForOutput(ClientAndServer mockServer) {
        mockServer.reset();
        Header header = new Header("Accept", "application/xml");
        mockServer.when(HttpRequest.request()
                .withMethod("GET")
                .withPath(MOCK_SERVER_PATH)
                .withQueryStringParameter("foo", "bar")
                .withHeaders(header))
                .respond(HttpResponse.response()
                        .withStatusCode(200)
                        .withBody("OK")
                );
    }


    private static void createExpectationForWrongOutput(ClientAndServer mockServer) {
        mockServer.reset();
        Header header = new Header("Accept", "application/xml");
        mockServer.when(HttpRequest.request()
                .withMethod("GET")
                .withPath(MOCK_SERVER_PATH)
                .withHeaders(header))
                .respond(HttpResponse.response()
                        .withStatusCode(400)
                        .withBody("Bad Request")
                );
    }

    private void createDropConnectionError(ClientAndServer mockServer){
        mockServer.reset();
        mockServer
                .when(
                        HttpRequest.request()
                                .withPath(MOCK_SERVER_PATH)
                )
                .error(
                        HttpError.error()
                                .withDropConnection(true)
                );
    }

    @Tag("fast")
    @Test
    void testGetResponseByPath(){
        String response = restUtil.get(API_PATH, String.class);
        Assertions.assertTrue(response.contains("OK"));
    }

    @Tag("fast")
    @Test
    void testGetResponseByPathAndParams(){
        List<Pair<String, String>> params = new LinkedList<>();
        params.add(Pair.of("foo", "bar"));
        String response = restUtil.get(API_PATH, params, String.class);
        Assertions.assertTrue(response.contains("OK"));
    }

    @Tag("fast")
    @Test
    void testGetErrorByPathAndParams(){
        createExpectationForWrongOutput(this.mockServer);
        Assertions.assertThrows(BadSolRequestException.class, () -> {restUtil.get(API_PATH, String.class);});
    }

    @Tag("fast")
    @Test
    void testGetErrorWithBadConnection(){
        createDropConnectionError(this.mockServer);
        Assertions.assertThrows(IllegalStateException.class, () -> {restUtil.get(API_PATH, String.class);});
    }
}
