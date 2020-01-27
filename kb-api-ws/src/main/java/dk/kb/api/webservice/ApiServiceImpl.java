package dk.kb.api.webservice;

import dk.kb.api.DefaultApi;
import dk.kb.api.config.KbApiServiceConfig;
import dk.kb.api.utilities.RestUtil;
import dk.kb.model.HelloReplyDto;
import dk.kb.model.IdMetaPairsDto;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiServiceImpl  implements DefaultApi {

    private static RestUtil rest;

    public ApiServiceImpl(){
        String  solrUrl = null;
        try {
            solrUrl = KbApiServiceConfig.getConfig().getString("config.kb-api-url");
        } catch (Exception e) {
            return;
        }
        rest = new RestUtil(solrUrl);
    }

    public String getCollectionByQuery(String collection, String q, String fq, String sort, Integer start, Integer rows, String fl, String df, String wt, Boolean facet, String facetField, String facetPrefix) {

        Map<String, String> params = new HashMap<String, String>();

        if(!q.isBlank()) {params.put("q", q);}
        if(!fq.isBlank()) {params.put("fq", fq);}
        if(!sort.isBlank()) {params.put("sort", sort);}
        params.put("start", start.toString());
        params.put("rows", rows.toString());
        if(!fl.isBlank()) {params.put("fl", fl);}
        if(!df.isBlank()) {params.put("df", df);}
        if(!wt.isBlank()) {params.put("wt", wt);}
        if (facet) {
            params.put("facet", "on");
            //TODO flere facet parms skal med
        }

        boolean isXml = isXml(wt);

        String response = rest.get(collection + "/select", params, isXml, String.class);

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
}
