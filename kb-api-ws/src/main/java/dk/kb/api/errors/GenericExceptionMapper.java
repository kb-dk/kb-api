package dk.kb.api.errors;

import dk.kb.model.ErrorDto;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    static String INTERNAL_SERVER_ERROR = "Something went wrong. Please repeat this operation later.";

    @Override
    public Response toResponse(Throwable exception) {
        ErrorDto errorMessage = new ErrorDto();
        errorMessage.setMessage(INTERNAL_SERVER_ERROR);
        errorMessage.setCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                entity(errorMessage).
                type(MediaType.APPLICATION_JSON).
                build();
    }
}
