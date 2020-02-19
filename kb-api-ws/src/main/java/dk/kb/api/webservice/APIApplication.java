package dk.kb.api.webservice;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import javax.ws.rs.core.Application;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class APIApplication extends Application {

    public Set<Class<?>> getClasses() {
      
        return new HashSet<>(Arrays.asList(JacksonJsonProvider.class, Service.class, ApiServiceImpl.class, 
                    dk.kb.api.errors.error400.BadSolrRequestExceptionMapper.class, 
                    dk.kb.api.errors.error404.SolrDataNotFoundExceptionMapper.class, 
                    dk.kb.api.errors.GenericExceptionMapper.class));

    }


}
