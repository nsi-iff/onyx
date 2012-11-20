package br.edu.iff.nsi.services.imageSearch;

import static br.edu.ifpi.opala.utils.Conversor.byteArrayToBufferedImage;

import java.util.*;

import br.edu.ifpi.opala.indexing.*;
import br.edu.ifpi.opala.utils.*;

public class OpalaImageSearchService implements ImageSearchService {

    @Override
    public Map<String, String> index(String id, byte[] fileContent) {
        MetaDocument metaDocument = new MetaDocument();
        metaDocument.setId(id);
        metaDocument.setTitle("title" + id);
        ImageIndexer indexer = ImageIndexerImpl.getImageIndexerImpl();
        ReturnMessage result = indexer.addImage(metaDocument,
            byteArrayToBufferedImage(fileContent));
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("code", String.valueOf(result.getCode()));
        resultMap.put("message", result.getMessage());
        return resultMap;
    }

    @Override
    public Map<String, String> remove(String id) {
        // TODO Auto-generated method stub
        return null;
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

}
