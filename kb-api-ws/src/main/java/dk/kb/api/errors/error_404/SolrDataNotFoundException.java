package dk.kb.api.errors.error_404;


import javax.ws.rs.WebApplicationException;

public class SolrDataNotFoundException extends WebApplicationException {
    private static final long serialVersionUID = 1L;

    public SolrDataNotFoundException(String message) {
        super(message);
    }
}
