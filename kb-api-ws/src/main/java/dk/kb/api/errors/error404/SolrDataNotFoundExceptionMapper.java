package dk.kb.api.errors.error404;


import dk.kb.model.ErrorDto;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class SolrDataNotFoundExceptionMapper implements ExceptionMapper<SolrDataNotFoundException> {

    @Override
    public Response toResponse(SolrDataNotFoundException e) {
        ErrorDto model = new ErrorDto();
        model.setCode(404);
        model.setMessage(e.getMessage());
        return Response.status(Response.Status.NOT_FOUND).entity(model).type(MediaType.APPLICATION_JSON).build();
    }
}

