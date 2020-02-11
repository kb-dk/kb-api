package dk.kb.api.errors.error_400;

import javax.ws.rs.WebApplicationException;

public class BadSolRequestException extends WebApplicationException {
    private static final long serialVersionUID = 1L;

    public BadSolRequestException(String message) {
        super(message);
    }
}
