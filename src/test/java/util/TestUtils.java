package util;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.DateTimeSchema;
import io.swagger.v3.oas.models.media.IntegerSchema;
import io.swagger.v3.oas.models.media.NumberSchema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.parser.OpenAPIV3Parser;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class TestUtils {

    private static final HttpClient client = HttpClientBuilder.create().build();

    public static OpenAPI retrieveApiDoc(String url) throws IOException {
        HttpResponse response = client.execute(new HttpGet(url));
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());

        String bodyAsString = EntityUtils.toString(response.getEntity());
        File temp = File.createTempFile("test", ".tmp");
        BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
        bw.write(bodyAsString);
        bw.close();

        return new OpenAPIV3Parser().read(temp.getPath());
    }

    public static OpenAPI retrieveOpenApiDoc(String url) throws IOException {
        return new OpenAPIV3Parser().read(url);
    }

    public static void verifyEndpoint(OpenAPI apiSpec, String path, HashMap<String, String> parameters ) {
        Object pathname;
        PathItem pathItem = apiSpec.getPaths().get(path);
        Assert.assertNotNull(pathItem);

        List<Parameter> parameterList = pathItem.getGet().getParameters();
        parameters.forEach((k, v) -> Assert.assertTrue(verifyParameter(k, v, parameterList)));
    }

    private static boolean verifyParameter(String key, String type, List<Parameter> parameterList) {
        AtomicBoolean isFound = new AtomicBoolean(false);
        parameterList.forEach(parameter -> {
            if(parameter.getName().equals(key) && parameter.getSchema().getType().equalsIgnoreCase(type)) isFound.set(true);
        });
        return isFound.get();
    }

    public static void verifyModel(OpenAPI apiSpec, String model) throws ClassNotFoundException {
        Class modelClass = Class.forName(model);
        String modelName = model.substring(model.lastIndexOf('.')+1);

        Map modelReturned = apiSpec.getComponents().getSchemas().get(modelName).getProperties();
        for (Field property :  modelClass.getDeclaredFields()) {
            if(property.getName() != "$jacocoData") {
                Assert.assertTrue(verifyProperty(property.getName(), property.getType().getSimpleName(), modelReturned));
            }
        }
    }

    private static boolean verifyProperty(String key, String type, Map model) {
        AtomicBoolean isFound = new AtomicBoolean(false);
        model.forEach((k, v) -> { if (k.equals(key)) {
            switch (type.toLowerCase()) {
                case "string":
                    if (((StringSchema) v).getType().equalsIgnoreCase(type)) isFound.set(true);
                    break;
                case "integer":
                    if (((IntegerSchema) v).getType().equalsIgnoreCase(type)) isFound.set(true);
                    break;
                case "double": case "float":
                    if (((NumberSchema) v).getFormat().equalsIgnoreCase(type)) isFound.set(true);
                    break;
                case "datetime":
                    if (((DateTimeSchema) v).getFormat().equalsIgnoreCase("date-time")) isFound.set(true);
                    break;
            }        }
        });
        return isFound.get();
    }

}