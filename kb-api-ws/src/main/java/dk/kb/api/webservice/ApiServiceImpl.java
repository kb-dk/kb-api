package dk.kb.api.webservice;

import dk.kb.api.DefaultApi;
import dk.kb.model.HelloReplyDto;

import java.io.File;
import java.util.List;

public class ApiServiceImpl  implements DefaultApi {
    @Override
    public HelloReplyDto getGreeting(String alternateHello) {
        return null;
    }

    @Override
    public List<Object> getMetaData(List<String> ids, String format) {
        return null;
    }

    @Override
    public File getResource(String id) {
        return null;
    }
}
