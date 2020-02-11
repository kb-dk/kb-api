package dk.kb.api.utilities;

import dk.kb.api.errors.error_400.BadSolRequestException;
import dk.kb.api.errors.error_404.SolrDataNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Map;


/**
 * <code>RESTUtil</code> acts as utility class used by REST API system
 */
public class RESTUtil {
    static final Logger logger = LoggerFactory.getLogger(RESTUtil.class);

    private final String url;

    public RESTUtil() {

        this.url = "";
    }

    public RESTUtil(String url) {

        this.url = url;
    }

    /**
     * Executes a REST URL with request path
     * @param path
     *          Rest request path
     * @return
     *          The HTTP response in form of String
     */
    public String get(String path) {
        return get(url, path, null, true, String.class);
    }


    /**
     * Executes a REST URL with request path and response type
     * @param path
     *          Rest request path
     * @param responseType
     *          Response type
     * @param <T>
     *          The requested response type
     * @return
     *          The HTTP response based on the given response type
     */
    public <T> T get(String path, Class<T> responseType){
        return get(url, path,  null, true, responseType);
    }

    /**
     * Executes a REST URL with request path and parameters
     * @param path
     *          Rest request path
     * @param params
     *          The map containing parameters in name-list pairs, whereas the list of each key contains 1 or more values.
     * @return
     *          The HTTP response in form of String
     */
    public String get(String path, Map<String, String> params){
        return get(url, path,  params, true, String.class);
    }

    /**
     * Executes a REST URL with request path, parameters and response type
     * @param path
     *          Rest request path
     * @param params
     *          The map containing parameters in name-list pairs, whereas the list of each key contains 1 value.
     * @param responseType
     *          Response type
     * @param <T>
     *          The requested response type
     * @return
     *          The HTTP response based on the given response type
     */
    public <T> T get(String path, Map<String, String> params, Class<T> responseType){
        return get(url, path,  params, true, responseType);
    }

    /**
     * Executes a REST URL
     * @param path
     *          Rest request path
     * @param params
     *          The map containing parameters in name-list pairs, whereas the list of each key contains 1 value.
     * @param xml
     *          <CODE>true</CODE> if content type as XML
     * @param responseType
     *          Response type
     * @param <T>
     *          The requested response type
     * @return
     *          The HTTP response based on the given response type
     */
    public <T> T get(String path, Map<String, String> params,  boolean xml, Class<T> responseType){
        return get(url, path,  params, xml, responseType);
    }

    /**
     * Executes a REST URL
     * @param url
     *          The REST URL.
     * @param path
     *          Rest request path
     * @param params
     *          The map containing parameters in name-list pairs, whereas the list of each key contains 1 value.
     * @param xml
     *          <CODE>true</CODE> if content type as XML
     * @param responseType
     *          Response type
     * @param <T>
     *          The requested response type
     * @return
     *          The HTTP response based on the given response type
     */
    public static <T> T get(String url, String path, Map<String, String> params, boolean xml, Class<T> responseType){
        Client client = ClientBuilder.newBuilder()
                .build();

        WebTarget target = client.target(url).path(path);
        target = addParams(target, params);

        //Test
        URI uri = target.getUri();
        logger.info("Get request by URL: {}", uri);

        Response resp = target.request(xml ? MediaType.APPLICATION_XML : MediaType.APPLICATION_JSON)
                .get(Response.class);

        /**
         * To read values:
         * List<Map<String, Object>> json = response.readEntity(new GenericType<List<Map<String, Object>>>() {});
         * String id = (String) json.get(0).get("id");
         */

        T response = null;
        try {
            if (resp.getStatus() == 200) {
                response = resp.readEntity(responseType);
            }
            if (resp.getStatus() == 400) {
                throw new BadSolRequestException("Bad Request");
            }
            if (resp.getStatus() == 404) {
                throw new SolrDataNotFoundException("Not Found");
            }
            if(resp.getStatus() != 400 && resp.getStatus() != 404 && resp.getStatus() != 200){
                throw new WebApplicationException("API exception");
            }
        }
        finally {
            if(resp != null){
                resp.close();
                }
        }
        return response;
    }

    /**
     *  The ping service is used to check if the server is reachable.
     * @return
     *          The OK response if service is online
     */
    public Response ping(){
        return Response.ok().entity("Service online").build();
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
