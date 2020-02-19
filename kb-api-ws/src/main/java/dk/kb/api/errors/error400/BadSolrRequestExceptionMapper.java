package dk.kb.api.errors.error400;


import dk.kb.model.ErrorDto;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BadSolrRequestExceptionMapper implements ExceptionMapper<BadSolRequestException> {

    @Override
    public Response toResponse(BadSolRequestException e) {
        ErrorDto model = new ErrorDto();
        model.setCode(400);
        model.setMessage(e.getMessage());
        return Response.status(Response.Status.BAD_REQUEST).entity(model).type(MediaType.APPLICATION_JSON).build();
    }
}

