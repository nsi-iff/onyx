package br.edu.iff.nsi.services.imageSearch;

import static br.edu.ifpi.opala.utils.Conversor.byteArrayToBufferedImage;

import java.awt.image.BufferedImage;
import java.util.*;

import br.edu.ifpi.opala.indexing.*;
import br.edu.ifpi.opala.searching.*;
import br.edu.ifpi.opala.utils.*;

public class OpalaImageSearchService implements ImageSearchService {

    private final ImageIndexer indexer;
    private final SearcherImage searcher;

    public OpalaImageSearchService() {
        indexer = ImageIndexerImpl.getImageIndexerImpl();
        searcher = new SearcherImageImpl();
    }

    @Override
    public Map<String, String> index(String key, byte[] fileContent) {
        MetaDocument metaDocument = new MetaDocument();
        metaDocument.setId(key);
        metaDocument.setTitle("title" + key);
        ReturnMessage result = indexer.addImage(metaDocument,
            byteArrayToBufferedImage(fileContent));
        return messageToMap(result);
    }

    @Override
    public Map<String, String> remove(String key) {
        return messageToMap(indexer.delImage(key));
    }

    @Override
    public List<Map<String, String>> search(byte[] fileContent) {
        return search(fileContent, 5);
    }

    @Override
    public List<Map<String, String>> search(byte[] fileContent, int limit) {
        BufferedImage image = byteArrayToBufferedImage(fileContent);
        SearchResult searchResult = searcher.search(image, limit);
        List<Map<String, String>> results = new LinkedList<Map<String,String>>();
        for (ResultItem item: searchResult.getItems()) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("key", item.getId());
            map.put("score", item.getScore());
            results.add(map);
        }
        return results;
    }

    private Map<String, String> messageToMap(ReturnMessage message) {
        Map<String, String> result = new HashMap<String, String>();
        result.put("code", String.valueOf(message.getCode()));
        result.put("message", message.getMessage());
        result.put("name", message.name());
        return result;
    }
}