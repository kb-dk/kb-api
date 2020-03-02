package dk.kb.api.webservice;


import dk.kb.api.errors.error400.BadSolRequestException;
import dk.kb.api.errors.error404.SolrDataNotFoundException;
import dk.kb.api.utilities.RESTUtil;
import dk.kb.model.ErrorDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApiServiceImplTest {

    @InjectMocks
    private ApiServiceImpl apiService;

    @Mock
    private RESTUtil restUtil;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        apiService = new ApiServiceImpl();
        apiService.setRest(restUtil);
    }

    @Tag("fast")
    @Test
    void testGetCollectionByQueryWith200() {
        String string = "";
        when(restUtil.get(anyString(), anyList(), anyBoolean(),  Mockito.eq(String.class))).thenReturn(string);
        Response response = apiService.getCollectionByQuery("collection", "qt", "q", new ArrayList<>(),  "sort",
                0, 10, "fl", "df", "wt", false, "facetField", "facetPrefix");
        Assertions.assertEquals(200, response.getStatus());

    }
    @Tag("fast")
    @Test
    void testGetCollectionByQueryWith400() {

        doThrow(BadSolRequestException.class).when(restUtil).get(anyString(), anyList(), anyBoolean(),  Mockito.eq(String.class));

        Assertions.assertThrows(BadSolRequestException.class, () -> {
            apiService.getCollectionByQuery("collection", "qt", "q", new ArrayList<>(),  "sort",
                    0, 10, "fl", "df", "wt", false, "facetField", "facetPrefix");
        });
    }
    @Tag("fast")
    @Test
    void testGetCollectionByQueryWith404() {

        doThrow(SolrDataNotFoundException.class).when(restUtil).get(anyString(), anyList(), anyBoolean(),  Mockito.eq(String.class));

        Assertions.assertThrows(SolrDataNotFoundException.class, () -> {
            apiService.getCollectionByQuery("collection", "qt", "q", new ArrayList<>(),  "sort",
                    0, 10, "fl", "df", "wt", false, "facetField", "facetPrefix");
        });
    }

}
