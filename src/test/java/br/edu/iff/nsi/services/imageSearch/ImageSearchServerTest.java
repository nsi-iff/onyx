package br.edu.iff.nsi.services.imageSearch;

import static br.edu.ifpi.opala.utils.Conversor.fileToByteArray;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.*;
import java.util.*;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.junit.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kevinsawicki.http.HttpRequest;


public class ImageSearchServerTest {
    private ImageSearchServer server;
    private final ObjectMapper mapper = new ObjectMapper();

    @SuppressWarnings("serial")
    @Test
    FAZER O INDEX (ADD) PRIMEIRO
    public void works() throws Exception {
        assertThat(
            mapper.readValue(
                HttpRequest
                    .post("http://localhost:9876/search")
                    .send("image=" + Base64.encodeBase64String(readFile("/dawn1.jpg")))
                    .accept("application/json")
                    .body(),
                new TypeReference<Map<String, String>>() {}),

                is(equalTo(new HashMap<String, String>() {{
                    put("");
                }})));
    }

    @Before
    public void serverUp() throws Exception {
        server = new ImageSearchServer(9876);
        server.start();
    }

    @After
    public void serverDown() throws Exception {
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