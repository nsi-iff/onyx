package br.edu.iff.nsi.services.imageSearch;

import static spark.Spark.*;

import java.lang.reflect.Method;
import java.util.*;

import org.apache.commons.codec.binary.Base64;

import spark.*;
import spark.servlet.SparkApplication;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ImageSearchServer implements SparkApplication {
    private final ImageSearchService service;
    private final ObjectMapper mapper;
    private int port;

    public ImageSearchServer() {
        service = new OpalaImageSearchService();
        mapper = new ObjectMapper();
        port = -1;
    }

    public ImageSearchServer(int port) {
        this();
        this.port = port;
    }

    public void init() {
        if (port != -1)
            setPort(port);

        put(new Route("/") {
            @Override
            public Object handle(Request request, Response response) {
                try {
                    String input = request.body();
                    Map<String, String> incoming = mapper.readValue(input,
                        new TypeReference<Map<String, String>>() { });
                    byte[] decoded = Base64.decodeBase64(incoming.get("image"));
                    Map<String, String> result = service.add(incoming.get("key"), decoded);
                    String json = mapper.writeValueAsString(result);
                    response.type("application/json");
                    return json;
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        delete(new Route("/") {
            @Override
            public Object handle(Request request, Response response) {
                try {
                    String input = request.body();
                    Map<String, String> incoming = mapper.readValue(input,
                        new TypeReference<Map<String, String>>() { });
                    Map<String, String> result = service.remove(incoming.get("key"));
                    String json = mapper.writeValueAsString(result);
                    response.type("application/json");
                    return json;
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        post(new Route("/") {
            @Override
            public Object handle(Request request, Response response) {
                try {
                    String input = request.body();
                    Map<String, String> incoming = mapper.readValue(input,
                        new TypeReference<Map<String, String>>() { });
                    byte[] decoded = Base64.decodeBase64(incoming.get("image"));
                    List<Map<String, String>> result = service.search(decoded);
                    String json = mapper.writeValueAsString(result);
                    response.type("application/json");
                    return json;
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

        });
    }

    public void stop() {
        try {
            Method method = Spark.class.getDeclaredMethod("stop");
            method.setAccessible(true);
            method.invoke(null);
            Thread.sleep(2000);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
