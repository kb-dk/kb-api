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
                .withPath("/solr/ds/admin/ping")
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
                .withPath("/solr/ds/admin/ping")
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
                .withPath("/solr/ds/admin/ping")
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
                                .withPath("/solr/ds/admin/ping")
                )
                .error(
                        HttpError.error()
                                .withDropConnection(true)
                );
    }

    @AfterEach
    public void stopServer() {
        mockServer.stop();
    }

    @Test
    void testGetResponseByPath(){
        String response = restUtil.get("/ds/admin/ping", String.class);
        Assertions.assertTrue(response.contains("OK"));
    }

    @Test
    void testGetResponseByPathAndParams(){
        List<Pair<String, String>> params = new LinkedList<>();
        params.add(Pair.of("foo", "bar"));
        String response = restUtil.get("/ds/admin/ping", params, String.class);
        Assertions.assertTrue(response.contains("OK"));
    }

    @Test()
    void testGetErrorByPathAndParams(){
        createExpectationForWrongOutput(this.mockServer);
        Assertions.assertThrows(BadSolRequestException.class, () -> {restUtil.get("/ds/admin/ping", String.class);});
    }

    @Test()
    void testGetErrorWithBadConnection(){
        createDropConnectionError(this.mockServer);
        Assertions.assertThrows(IllegalStateException.class, () -> {restUtil.get("/ds/admin/ping", String.class);});
    }
}
