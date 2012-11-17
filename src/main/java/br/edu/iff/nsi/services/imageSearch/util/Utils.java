package br.edu.iff.nsi.services.imageSearch.util;

import java.io.*;

import org.apache.commons.io.IOUtils;

public class Utils {
    public static String readFile(String fileName) {
        InputStream stream = Utils.class.getResourceAsStream("/" + fileName);
        StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(stream, writer);
            return writer.toString();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
