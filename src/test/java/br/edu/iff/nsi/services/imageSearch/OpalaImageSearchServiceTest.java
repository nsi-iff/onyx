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

    @Before
    public void init() {
        Util.deleteDir(new File(Path.IMAGE_INDEX.getValue()));
    }

    @Test
    public void index() throws IOException {
        service = new OpalaImageSearchService();
        @SuppressWarnings("serial")
        Map<String, String> map = new HashMap<String, String>() {{
            put("code", String.valueOf(ReturnMessage.SUCCESS.getCode()));
            put("message", ReturnMessage.SUCCESS.getMessage());
        }};

        assertThat(service.index("fruits", readFile("/tropical_fruits.jpg")),
                is(equalTo(map)));
        assertThat(service.index("dawn1", readFile("/dawn1.jpg")),
                is(equalTo(map)));
        assertThat(service.index("dawn2", readFile("/dawn2.jpg")),
                is(equalTo(map)));
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