package br.edu.iff.nsi.services.imageSearch;

import org.restlet.resource.*;

public class ImageSearchService extends ServerResource{
    @Get
    public String represent() {
        return "Hello world!";
    }
}