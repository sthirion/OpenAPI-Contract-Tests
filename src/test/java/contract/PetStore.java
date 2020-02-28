package contract;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.Before;
import org.junit.Test;
import util.TestUtils;

import java.io.IOException;
import java.util.HashMap;

public class PetStore {

    // Contract tests for https://swagger.developerhub.io/swagger-petstore/ref
    private final static String openAPIUrl = "https://petstore.swagger.io/v2/swagger.json";
    private OpenAPI apiSpecDocument;

    @Before
    public void setup() throws IOException {
        apiSpecDocument = TestUtils.retrieveOpenApiDoc(openAPIUrl);
    }

    @Test
    public void findPetByStatus() throws ClassNotFoundException {
        String modelClass = "model.petstore.Pet";
        String endpoint = "/pet/findByStatus";

        HashMap<String, String> parametersUsed = new HashMap<>();
        parametersUsed.put("status", "String");

        // VERIFY QUERYSTRING
        TestUtils.verifyEndpoint(apiSpecDocument, endpoint, parametersUsed);

        // VERIFY MODEL
        TestUtils.verifyModel(apiSpecDocument, modelClass);
    }

}
