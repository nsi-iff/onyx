package br.edu.iff.nsi.services.imageSearch;

import static br.edu.ifpi.opala.utils.Conversor.fileToByteArray;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.*;
import java.util.*;

import org.apache.commons.io.FileUtils;
import org.junit.*;

import br.edu.ifpi.opala.utils.*;

public class OpalaImageSearchServiceTest {
    private ImageSearchService service;
    private Map<String, String> success, notFound;

    @Before
    public void resetIndices() {
        Util.deleteDir(new File(Path.IMAGE_INDEX.getValue()));
    }

    @Before
    public void initAttributes() {
        service = new OpalaImageSearchService();
        success = messageToMap(ReturnMessage.SUCCESS);
        notFound = messageToMap(ReturnMessage.ID_NOT_FOUND);
    }

    @Test
    public void index() throws IOException {
        assertThat(service.index("fruits", readFile("/tropical_fruits.jpg")),
                is(equalTo(success)));
        assertThat(service.index("dawn1", readFile("/dawn1.jpg")),
                is(equalTo(success)));
        assertThat(service.index("dawn2", readFile("/dawn2.jpg")),
                is(equalTo(success)));
    }

    @Test
    public void remove() {
        service.index("fruits", readFile("/tropical_fruits.jpg"));
        assertThat(service.remove("fruits"), is(equalTo(success)));
        assertThat(service.remove("fruits"), is(equalTo(notFound)));
    }

    @Test
    public void search() {
        service.index("fruits", readFile("/tropical_fruits.jpg"));
        service.index("dawn1", readFile("/dawn1.jpg"));

        List<Map<String, String>> results = service.search(readFile("/dawn2.jpg"));
        assertThat(results.get(0).get("id"), is(equalTo("dawn1")));
        assertThat(results.get(1).get("id"), is(equalTo("fruits")));
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

    private Map<String, String> messageToMap(ReturnMessage message) {
        Map<String, String> result = new HashMap<String, String>();
        result.put("code", String.valueOf(message.getCode()));
        result.put("message", message.getMessage());
        result.put("name", message.name());
        return result;
    }
}