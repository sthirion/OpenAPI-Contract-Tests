package contract;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.Before;
import org.junit.Test;
import util.TestUtils;

import java.io.IOException;
import java.util.HashMap;

public class GSA {

    // Contract tests for https://cityPairsPrototypeAPI.app.cloud.gov/travel/citypairs/v0
    private final static String openAPIUrl = "https://gsa.github.io/api-documentation-template/api-docs/console/citypairs.json";
    private OpenAPI apiSpecDocument;

    @Before
    public void setup() throws IOException {
        apiSpecDocument = TestUtils.retrieveOpenApiDoc(openAPIUrl);
    }

    @Test
    public void airfares() throws ClassNotFoundException {
        String modelClass = "model.Airfare";
        String endpoint = "/airfares";

        HashMap<String, String> parametersUsed = new HashMap<>();
        parametersUsed.put("award_year", "String");
        parametersUsed.put("origin_airport_abbrev", "String");

        // VERIFY QUERYSTRING
        TestUtils.verifyEndpoint(apiSpecDocument, endpoint, parametersUsed);

        // VERIFY MODEL
        TestUtils.verifyModel(apiSpecDocument, modelClass);
    }

}
