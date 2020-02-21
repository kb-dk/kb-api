package dk.kb.api.integration;

import dk.kb.api.errors.error400.BadSolRequestException;
import dk.kb.api.errors.error404.SolrDataNotFoundException;
import dk.kb.api.utilities.RESTUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Because the test uses local installation of Solr, we will control when the test runs. Therefore test implemented using main method.
 */
public class RestUtilTest {
    static final Logger logger = LoggerFactory.getLogger(RestUtilTest.class);
    static String solrUrl = "http://localhost:10007/solr/";
    static String solrPath = "ds/admin/ping";

    static boolean testGetByPath(){
        RESTUtil restUtil = new RESTUtil(solrUrl);
        String result = restUtil.get(solrPath);
        return result.contains("\"status\":\"OK\"");
    }

    static boolean testGetByUrlPathParamAndStatus200(){
        RESTUtil restUtil = new RESTUtil();
        Map<String, String> params = new HashMap<String, String>();
        params.put("wt", "json");
        params.put("distrib", "true");
        String result = restUtil.get(solrUrl, solrPath, params, true, String.class);
        return result.contains("\"status\":\"OK\"");
    }
    static boolean testGetByUrlPathParamAndStatus400(){
        RESTUtil restUtil = new RESTUtil();
        Map<String, String> params = new HashMap<String, String>();
        params.put("wt", "json");
        params.put("distrib", "!true");
        String result = "";
        try {
            result = restUtil.get(solrUrl, solrPath, params, true, String.class);
        }catch (BadSolRequestException exception){
            return result.contains("\"code\":\"400\"");
        }
        return false;
    }
    static boolean testGetByUrlPathParamAndStatus404(){
        RESTUtil restUtil = new RESTUtil();
        Map<String, String> params = new HashMap<String, String>();
        params.put("wt", "json");
        params.put("distrib", "true");
        String badSolrPath = "!ds/admin/ping";
        String result = "";
        try {
            result = restUtil.get(solrUrl, badSolrPath, params, true, String.class);
        }catch (SolrDataNotFoundException exception){
            return exception.getMessage().contains("Not Found");
        }
        return false;
    }

    static boolean testGetByPathParam(){
        RESTUtil restUtil = new RESTUtil(solrUrl);
        Map<String, String> params = new HashMap<String, String>();
        params.put("wt", "json");
        params.put("distrib", "true");
        String result = restUtil.get(solrPath, params, true, String.class);
        return result.contains("\"status\":\"OK\"");
    }

    static boolean testGetByPathAndListOfPairParam(){
        RESTUtil restUtil = new RESTUtil(solrUrl);
        List<Pair<String, String>> params = new ArrayList<Pair<String, String>>();
        Pair<String, String> param_1 = Pair.of("wt", "json");
        Pair<String, String> param_2 = Pair.of("distrib", "true");
        Pair<String, String> param_3 = Pair.of("fq", "type:image");
        Pair<String, String> param_4 = Pair.of("fq", "title:Maria");
        params.add(param_1);
        params.add(param_2);
        params.add(param_3);
        params.add(param_4);
        String result = restUtil.get(solrPath, params, true, String.class);
        return result.contains("\"status\":\"OK\"");
    }

    public static void main(String[] args) {
        logger.info("RESTUtil get(path) is tested: {}", testGetByPath());
        logger.info("RESTUtil get(url, path, params, xml, responseType) is tested with status 200: {}", testGetByUrlPathParamAndStatus200());
        logger.info("RESTUtil get(url, path, params, xml, responseType) is tested with status 400: {}", testGetByUrlPathParamAndStatus400());
        logger.info("RESTUtil get(url, path, params, xml, responseType) is tested with status 404: {}", testGetByUrlPathParamAndStatus404());
        logger.info("RESTUtil get(path, params, xml, responseType) is tested: {}", testGetByPathParam());
        logger.info("RESTUtil get(path, params as list, xml, responseType) is tested: {}", testGetByPathAndListOfPairParam());
    }
}
