package br.edu.iff.nsi.services.imageSearch;

import java.util.*;

public interface ImageSearchService {
    /**
     * Index an image and returns a hash containing the result.
     * @param key
     * @param fileContent
     * @return a hash with two keys: "code" and "message". Its values follow
     *          Opala's ReturnMessage values.
     */
    Map<String, String> index(String key, byte[] fileContent);

    /**
     * Removes an image from index.
     * @param key
     * @return a hash with two keys: "code" and "message". Its values follow
     *          Opala's ReturnMessage values.
     */
    Map<String, String> remove(String key);

    /**
     * Searches for images using given image. A limit for result count can be
     * provided.
     * @param fileContent
     * @return a list of hashes, each having "key" and "score".
     */
    List<Map<String, String>> search(byte[] fileContent);
    List<Map<String, String>> search(byte[] fileContent, int limit);
}