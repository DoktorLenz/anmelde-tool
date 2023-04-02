package de.stinner.anmeldetool.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;

import static org.assertj.core.api.Fail.fail;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@WithMockUser
public abstract class BaseControllerTest extends BaseIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    //Wrapper so that static import is not necessary in child-classes
    protected static StatusResultMatchers status() {
        return MockMvcResultMatchers.status();
    }

    //Wrapper so that static import is not necessary in child-classes
    protected static ContentResultMatchers content() {
        return MockMvcResultMatchers.content();
    }

    protected ResultActions performGetRequest(
            String path,
            String query,
            Object... uriVars
    ) {
        return performGetRequest(path + query, uriVars);
    }

    protected ResultActions performGetRequest(
            String path,
            Object... uriVars
    ) {
        ResultActions resultActions = null;

        try {
            resultActions = mockMvc.perform(
                    getGetBuilder(path, uriVars).with(csrf())
            );
        } catch (Exception e) {
            fail("Get-request failed.", e);
        }

        return resultActions;
    }

    public MockHttpServletRequestBuilder getGetBuilder(
            String basePath,
            Object... uriVars
    ) {
        return MockMvcRequestBuilders.get(basePath, uriVars);
    }

    protected ResultActions performPutRequest(
            Object body,
            String path,
            Object... uriVars
    ) {
        ResultActions resultActions = null;

        try {
            resultActions = mockMvc.perform(
                    getPutBuilder(path, serializeObjectToJson(body), uriVars).with(csrf())
            );
        } catch (Exception e) {
            fail("Get-request failed.", e);
        }

        return resultActions;
    }

    public MockHttpServletRequestBuilder getPutBuilder(
            String basePath,
            String jsonContent,
            Object... uriVars
    ) {
        return MockMvcRequestBuilders
                .put(basePath, uriVars)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent);
    }

    public String serializeObjectToJson(Object object) throws JsonProcessingException {
        if (object != null) {
            return getObjectWriter().writeValueAsString(object);
        } else {
            return "";
        }
    }

    private ObjectWriter getObjectWriter() {
        return getObjectMapper().writer().withDefaultPrettyPrinter();
    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }

    protected ResultActions performPostRequest(
            Object body,
            String path,
            Object... uriVars
    ) {
        ResultActions resultActions = null;

        try {
            resultActions = mockMvc.perform(
                    getPostBuilder(path, serializeObjectToJson(body), uriVars).with(csrf())
            );
        } catch (Exception e) {
            fail("Get-request failed.", e);
        }

        return resultActions;
    }

    public MockHttpServletRequestBuilder getPostBuilder(
            String basePath,
            String jsonContent,
            Object... uriVars
    ) {
        return MockMvcRequestBuilders
                .post(basePath, uriVars)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent);
    }

    protected ResultActions performDeleteRequest(
            String path,
            Object... uriVars
    ) {
        ResultActions resultActions = null;


        try {
            resultActions = mockMvc.perform(
                    getDeleteBuilder(path, uriVars).with(csrf())
            );
        } catch (Exception e) {
            fail("Get-request failed.", e);
        }

        return resultActions;
    }

    public MockHttpServletRequestBuilder getDeleteBuilder(
            String path,
            Object... uriVars
    ) {
        return MockMvcRequestBuilders.delete(path, uriVars);
    }

    public <T> T deserializeJsonIntoSingleObject(
            String json,
            Class<T> objectType
    )
            throws JsonProcessingException {
        return getObjectMapper().readValue(json, objectType);
    }


}
