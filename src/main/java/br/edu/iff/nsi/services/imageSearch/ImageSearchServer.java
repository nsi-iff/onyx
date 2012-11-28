package br.edu.iff.nsi.services.imageSearch;

import static spark.Spark.*;

import java.util.Map;

import org.apache.commons.codec.binary.Base64;

import spark.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ImageSearchServer<E> {
    private final ImageSearchService service;
    private final ObjectMapper mapper;
    private final int port;

    public ImageSearchServer(int port) {
        service = new OpalaImageSearchService();
        mapper = new ObjectMapper();
        this.port = port;
    }

    public void start() {
        setPort(port);

        post(new Route("/search") {
            @Override
            public Object handle(Request request, Response response) {
                try {
                    Map<String, String> incoming = mapper.readValue(request.body(),
                        new TypeReference<Map<String, String>>() { });
                    byte[] decoded = Base64.decodeBase64(incoming.get("image"));
                    return mapper.writeValueAsBytes(service.search(decoded));
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
