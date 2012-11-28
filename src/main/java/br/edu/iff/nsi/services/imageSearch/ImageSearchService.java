package br.edu.iff.nsi.services.imageSearch;

import java.util.*;

public interface ImageSearchService {
    /**
     * Index an image and returns a hash containing the result.
     * @param id
     * @param fileContent
     * @return a hash with two keys: "code" and "message". Its values follow
     *          Opala's ReturnMessage values.
     */
    Map<String, String> add(String id, byte[] fileContent);

    /**
     * Removes an image from index.
     * @param id
     * @return a hash with two keys: "code" and "message". Its values follow
     *          Opala's ReturnMessage values.
     */
    Map<String, String> remove(String id);

    /**
     * Searches for images using given image. A limit for result count can be
     * provided.
     * @param fileContent
     * @return a list of hashes, each having "id" and "score".
     */
    List<Map<String, String>> search(byte[] fileContent);
    List<Map<String, String>> search(byte[] fileContent, int limit);
}