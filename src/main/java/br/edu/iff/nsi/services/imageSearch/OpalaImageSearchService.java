package br.edu.iff.nsi.services.imageSearch;

import static br.edu.ifpi.opala.utils.Conversor.byteArrayToBufferedImage;

import java.util.*;

import br.edu.ifpi.opala.indexing.*;
import br.edu.ifpi.opala.utils.*;

public class OpalaImageSearchService implements ImageSearchService {

    private final ImageIndexer indexer;

    public OpalaImageSearchService() {
        indexer = ImageIndexerImpl.getImageIndexerImpl();
    }

    @Override
    public Map<String, String> index(String id, byte[] fileContent) {
        MetaDocument metaDocument = new MetaDocument();
        metaDocument.setId(id);
        metaDocument.setTitle("title" + id);
        ReturnMessage result = indexer.addImage(metaDocument,
            byteArrayToBufferedImage(fileContent));
        return messageToMap(result);
    }

    @Override
    public Map<String, String> remove(String id) {
        return messageToMap(indexer.delImage(id));
    }

    @Override
    public List<Map<String, String>> search(String fileContent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, String>> search(String fileContent, int limit) {
        // TODO Auto-generated method stub
        return null;
    }

    private Map<String, String> messageToMap(ReturnMessage message) {
        Map<String, String> result = new HashMap<String, String>();
        result.put("code", String.valueOf(message.getCode()));
        result.put("message", message.getMessage());
        result.put("name", message.name());
        return result;
    }
}
