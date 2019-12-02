package dk.kb.api.webservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ArrayList;

@Path("/")
public class Service {

    private final Logger log = LoggerFactory.getLogger(getClass());
    
    public Service() {
        log.info("Initializing service");
    }
    
    @GET
    @Path("getHello")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHello() {
        List<String> helloLines = new ArrayList<>();
        helloLines.add("Hello");
        helloLines.add("world");
        return Response.ok(helloLines, MediaType.APPLICATION_JSON).build();
    }


    
}




