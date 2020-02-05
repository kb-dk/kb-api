package dk.kb.api.webservice;

import dk.kb.api.DefaultApi;
import dk.kb.api.config.KbApiServiceConfig;
import dk.kb.api.utilities.RESTUtil;
import dk.kb.model.HelloReplyDto;
import dk.kb.model.IdMetaPairsDto;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  Provides a basic service to perform an API request.
 */
public class ApiServiceImpl  implements DefaultApi {

    private static RESTUtil rest;

    /**
     * Constructs an instance of this class
     */
    public ApiServiceImpl(){
        String  solrUrl = null;
        try {
            solrUrl = KbApiServiceConfig.getConfig().getString("config.kb-api-url");
        } catch (Exception e) {
            return;
        }
        rest = new RESTUtil(solrUrl);
    }

    /**
     * Acts as a proxy to a backing Solr and returns the result directly
     *
     * @param collection
     *          A unique identifier for the collections search
     * @param qt
     *          Request handler
     * @param q
     *          Query string for solr data records
     * @param fq
     *          The list containing filter queries
     * @param sort
     *          Sort field/direction.
     * @param start
     *          Number of leading documents to skip
     * @param rows
     *          Max results per page
     * @param fl
     *         Comma separated field List
     * @param df
     *          Default field
     * @param wt
     *          Response writer
     * @param facet
     *          <code>true</code> if faceting is enabled
     * @param facetField
     *          The facet field parameter
     * @param facetPrefix
     *          The facet prefix parameter
     * @return
     *          The Solr response in form of String
     */
    public String getCollectionByQuery(String collection, String qt, String q, List<String> fq, String sort, Integer start, Integer rows, String fl, String df, String wt, Boolean facet, String facetField, String facetPrefix) {

        Map<String, String> params = new HashMap<String, String>();

        if(!q.isBlank()) {params.put("q", q);}

        for(int i = 0; i < fq.size(); i++){
            params.put("fq", fq.get(i));
        }

        if(!sort.isBlank()) {params.put("sort", sort);}
        params.put("start", start.toString());
        params.put("rows", rows.toString());
        if(!fl.isBlank()) {params.put("fl", fl);}
        if(!df.isBlank()) {params.put("df", df);}
        if(!wt.isBlank()) {params.put("wt", wt);}
        if (facet) {
            params.put("facet", "on");
            params.put("facet.field", facetField);
            params.put("facet.prefix", facetPrefix);
        }

        boolean isXml = isXml(wt);

        String response = rest.get(collection + "/" + qt, params, isXml, String.class);

        return response;
    }

    private void   putRawQueryParameters(String rawQueryParameters, Map<String, String> params) {
        if(!rawQueryParameters.equalsIgnoreCase("")) {
            String[] rowString = rawQueryParameters.split("&");
            for (int i = 0; i < rowString.length; i++) {
                String[] string = rowString[i].split("=");
                params.put(string[0], string[1]);
            }
        }
    }

    private boolean isXml(String wt) {
        boolean isXml = false;
        if (wt != null) {
            switch (wt) {
                case ("xml"):
                    isXml = true;
                    break;
                default:
                    isXml = false;
            }
        }
        return isXml;
    }

    @Override
    public HelloReplyDto getGreeting(String alternateHello) {
        HelloReplyDto helloReplyDto = new HelloReplyDto();
        helloReplyDto.setMessage("Hello back");
        return helloReplyDto;
    }

    @Override
    public  List<IdMetaPairsDto> getMetaData(List<String> ids, String format) {
        return null;
    }

    @Override
    public File getResource(String id) {
        return null;
    }

    @Override
    public String ping(){
        return rest.ping().getEntity().toString();
    }

}
