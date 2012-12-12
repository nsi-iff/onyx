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

public class ImageSearchServerTest {
    private static ImageSearchServer server;
    private final ObjectMapper mapper = new ObjectMapper();

    private static final int port = 9876;

    @Test
    public void add() throws Exception {
        Map<String, String> input = null;
        String responseBody = Request
                .Put("http://localhost:" + port + "/")
                .bodyString(String.format("{%s, %s}", 
                                encodedImage("/dawn1.jpg"),
                                key("a123")),
                            ContentType.APPLICATION_JSON)
                .execute()
                .returnContent()
                .asString();
        input = mapper.readValue(
                responseBody,
                new TypeReference<Map<String, String>>() {});
        assertThat(input.get("code"), is(equalTo(String.valueOf(SUCCESS.getCode()))));
        assertThat(input.get("message"), is(equalTo(SUCCESS.getMessage())));
    }

    //@Test
    public void remove() throws Exception {
        Map<String, String> input = null;
        // add image
        Request
            .Put("http://localhost:" + port + "/")
            .bodyString("{\"image\":\"" + Base64.encodeBase64String(readFile("/dawn1.jpg")) + "\"," +
                    "\"key\":\"a123\"}", ContentType.APPLICATION_JSON)
            .execute();

        // remove image
        String responseBody = Request
            .Delete("http://localhost:" + port + "/")
            .bodyString("{\"key\":\"a123\"}", ContentType.APPLICATION_JSON)
            .execute()
            .returnContent()
            .asString();

            input = mapper.readValue(
                    responseBody,
                    new TypeReference<Map<String, String>>() {});
        assertThat(input.get("code"), is(equalTo(String.valueOf(SUCCESS.getCode()))));
        assertThat(input.get("message"), is(equalTo(SUCCESS.getMessage())));
    }

    //@Test
    public void search() throws Exception {
        List<Map<String, String>> results = null;
        // add images
        Request
            .Put("http://localhost:" + port + "/")
            .bodyString(
                "{\"image\":\"" + Base64.encodeBase64String(readFile("/dawn1.jpg")) + "\"," +
                "\"key\":\"dawn1\"}", ContentType.APPLICATION_JSON)
            .execute();
        Request
            .Put("http://localhost:" + port + "/")
            .bodyString(
                "{\"image\":\"" + Base64.encodeBase64String(readFile("/tropical_fruits.jpg")) + "\"," +
                "\"key\":\"tropical\"}", ContentType.APPLICATION_JSON)
            .execute();

        // search image
        String responseBody = Request
            .Post("http://localhost:" + port + "/")
            .bodyString(
                "{\"image\":\"" + Base64.encodeBase64String(readFile("/dawn2.jpg")) + "\"}",
                ContentType.APPLICATION_JSON)
            .execute()
            .returnContent()
            .asString();

            results = mapper.readValue(
                    responseBody,
                    new TypeReference<List<Map<String, String>>>() {});
        assertThat(results.size(), is(equalTo(2)));
        Map<String, String> result1, result2;
        result1 = results.get(0);
        result2 = results.get(1);
        assertThat(result1.get("key"), is(equalTo("dawn1")));
        assertThat(result2.get("key"), is(equalTo("tropical")));
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
    
    private String encodedImage(String imageFile) {
        return String.format("\"image\":\"%s\"", 
                Base64.encodeBase64String(readFile(imageFile)));
    }
    
    private String key(String key) {
        return String.format("\"key\": \"%s\"", key);
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