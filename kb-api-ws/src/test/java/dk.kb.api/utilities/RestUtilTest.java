package dk.kb.api.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class RestUtilTest {
    static final Logger logger = LoggerFactory.getLogger(RestUtilTest.class);
    static String solrUrl = "http://localhost:10007/solr/";
    static String solrPath = "ds/admin/ping";

    static boolean testGetByPath(){
        RESTUtil restUtil = new RESTUtil(solrUrl);
        String result = restUtil.get(solrPath);
        return result.contains("\"status\":\"OK\"");
    }

    static boolean testGetByUrlPathParam(){
        RESTUtil restUtil = new RESTUtil();
        Map<String, String> params = new HashMap<String, String>();
        params.put("wt", "json");
        params.put("distrib", "true");
        String result = restUtil.get(solrUrl, solrPath, params, true, String.class);
        return result.contains("\"status\":\"OK\"");
    }

    static boolean testGetByPathParam(){
        RESTUtil restUtil = new RESTUtil(solrUrl);
        Map<String, String> params = new HashMap<String, String>();
        params.put("wt", "json");
        params.put("distrib", "true");
        String result = restUtil.get(solrPath, params, true, String.class);
        return result.contains("\"status\":\"OK\"");
    }

    public static void main(String[] args) {
        logger.info("RESTUtil get(path) is tested: {}", testGetByPath());
        logger.info("RESTUtil get(url, path, params, xml, responseType) is tested: {}", testGetByUrlPathParam());
        logger.info("RESTUtil get(path, params, xml, responseType) is tested: {}", testGetByPathParam());
    }
}
