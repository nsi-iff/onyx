package br.edu.iff.nsi.services.imageSearch;

import static br.edu.ifpi.opala.utils.Conversor.fileToByteArray;
import static br.edu.ifpi.opala.utils.ReturnMessage.SUCCESS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.*;
import java.util.*;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.junit.*;

import br.edu.ifpi.opala.utils.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kevinsawicki.http.HttpRequest;

public class ImageSearchServerTest {
    private static ImageSearchServer server;
    private final ObjectMapper mapper = new ObjectMapper();

    private static final int port = 9876;

    @Test
    public void add() {
        Map<String, String> input = null;
        try {
            String requestBody = HttpRequest
                    .put("http://localhost:" + port + "/")
                    .accept("application/json")
                    .send("{\"image\":\"" + Base64.encodeBase64String(readFile("/dawn1.jpg")) + "\"," +
                            "\"code\":\"a123\"}")
                    .body();
            input = mapper.readValue(
                    requestBody,
                    new TypeReference<Map<String, String>>() {});
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
        assertThat(input.get("code"), is(equalTo(String.valueOf(SUCCESS.getCode()))));
        assertThat(input.get("message"), is(equalTo(SUCCESS.getMessage())));
    }

    //@Test
    public void remove() {
        Map<String, String> input = null;
        try {
            // add image
            HttpRequest
                .put("http://localhost:" + port + "/")
                .accept("application/json")
                .send("{\"image\":\"" + Base64.encodeBase64String(readFile("/dawn1.jpg")) + "\"," +
                        "\"code\":\"a123\"}");

            // remove image
            String requestBody = Request
                .Delete("http://localhost:" + port + "/")
                .bodyString("{\"code\":\"a123\"}", ContentType.APPLICATION_JSON)
                .execute()
                .returnContent()
                .asString();

            input = mapper.readValue(
                    requestBody,
                    new TypeReference<Map<String, String>>() {});
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
        assertThat(input.get("code"), is(equalTo(String.valueOf(SUCCESS.getCode()))));
        assertThat(input.get("message"), is(equalTo(SUCCESS.getMessage())));
    }

    @Test
    public void search() {
        List<Map<String, String>> results = null;
        try {
            // add image
            HttpRequest
                .put("http://localhost:" + port + "/")
                .accept("application/json")
                .send("{\"image\":\"" + Base64.encodeBase64String(readFile("/dawn1.jpg")) + "\"," +
                        "\"code\":\"dawn1\"}").body();
            HttpRequest
                .put("http://localhost:" + port + "/")
                .accept("application/json")
                .send("{\"image\":\"" + Base64.encodeBase64String(readFile("/tropical_fruits.jpg")) + "\"," +
                        "\"code\":\"tropical\"}").body();

            // search image
            String requestBody = HttpRequest
                .post("http://localhost:" + port + "/")
                .accept("application/json")
                .send("{\"image\":\"" + Base64.encodeBase64String(readFile("/dawn2.jpg")) + "\"}")
                .body();

            results = mapper.readValue(
                    requestBody,
                    new TypeReference<List<Map<String, String>>>() {});
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
        assertThat(results.size(), is(equalTo(2)));
        Map<String, String> result1, result2;
        result1 = results.get(0);
        result2 = results.get(1);
        assertThat(result1.get("id"), is(equalTo("dawn1")));
        assertThat(result2.get("id"), is(equalTo("tropical")));
        assertThat(Double.parseDouble(result1.get("score")),
                is(greaterThan(Double.parseDouble(result2.get("score")))));
    }

    @BeforeClass
    public static void serverUp() throws Exception {
        server = new ImageSearchServer(port);
        server.init();
    }

    @Before
    public void clearIndex() {
        Util.deleteDir(new File(Path.IMAGE_INDEX.toString()));
    }

    @AfterClass
    public static void serverDown() throws Exception {
        server.stop();
    }

    private byte[] readFile(String fileName) {
        File imageFile = FileUtils.toFile(getClass().getResource(fileName));
        try {
            return fileToByteArray(imageFile);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}