package dk.kb.api.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestUtilTest {
    static final Logger logger = LoggerFactory.getLogger(RestUtilTest.class);
    static String solrUrl = "http://localhost:10007/solr/";

    static boolean testGet(){
        RESTUtil restUtil = new RESTUtil(solrUrl);
        String result = restUtil.get("ds/admin/ping");
        return result.contains("\"status\":\"OK\"");
    }

    public static void main(String[] args) {
        boolean isTested = testGet();
        logger.info("RESTUtil get(path) is tested: {}", isTested);
    }
}
