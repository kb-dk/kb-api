package dk.kb.api.webservice;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import dk.kb.api.errors.error_400.BadSolrRequestExceptionMapper;
import dk.kb.api.errors.error_404.SolrDataNotFoundExceptionMapper;

import javax.ws.rs.core.Application;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class APIApplication extends Application {

    public Set<Class<?>> getClasses() {
        return new HashSet<>(Arrays.asList(JacksonJsonProvider.class, Service.class, ApiServiceImpl.class));
    }


}
