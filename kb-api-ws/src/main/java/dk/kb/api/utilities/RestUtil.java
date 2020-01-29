package dk.kb.api.utilities;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Map;

public class RestUtil {

    private final String server;

    public RestUtil(String url) {

        this.server = url;
    }

    public String get(String path) {
        return get(server, path, null, true, String.class);
    }

    public <T> T get(String path, Class<T> responseType) {
        return get(server, path,  null, true, responseType);
    }

    public String get(String path, Map<String, String> params) {
        return get(server, path,  params, true, String.class);
    }

    public <T> T get(String path, Map<String, String> params, Class<T> responseType) {
        return get(server, path,  params, true, responseType);
    }
    public <T> T get(String path, Map<String, String> params,  boolean xml, Class<T> responseType) {
        return get(server, path,  params, xml, responseType);
    }

    public static <T> T get(String server, String path, Map<String, String> params, boolean xml, Class<T> responseType) {
        Client client = ClientBuilder.newBuilder()
                .build();

        WebTarget target = client.target(server).path(path);
        target = addParams(target, params);

        //Test
        URI uri = target.getUri();

        Response resp = target.request(xml ? MediaType.APPLICATION_XML : MediaType.APPLICATION_JSON)
                .get();
        /*
         * Hvis man vil læse værdier:
         * List<Map<String, Object>> json = response.readEntity(new GenericType<List<Map<String, Object>>>() {});
         * String id = (String) json.get(0).get("id");
         */
        T response = null;
        try {
            if (resp.getStatus() == 200) {
                response = resp.readEntity(responseType);
            }
        } finally {
            resp.close();
        }
        return response;
    }

    private static WebTarget addParams(WebTarget target, Map<String, String> params) {
        if (params == null) return target;
        WebTarget result = target;
        for (String key: params.keySet()) {
            String value = params.get(key);
            result = result.queryParam(key, value);
        }
        return result;
    }
}
