package br.edu.iff.nsi.services.imageSearch.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

public class UtilsTest {
    @Test
    public void readFile() {
        assertThat(Utils.readFile("a_text_file.txt"),
                is(equalTo("A text file.")));
    }
}
