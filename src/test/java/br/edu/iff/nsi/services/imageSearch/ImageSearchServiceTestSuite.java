package br.edu.iff.nsi.services.imageSearch;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import br.edu.iff.nsi.services.imageSearch.util.UtilsTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    OpalaImageSearchServiceTest.class,
    ServiceTest.class,
    UtilsTest.class
})
public class ImageSearchServiceTestSuite {

}
