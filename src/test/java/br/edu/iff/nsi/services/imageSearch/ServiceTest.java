package br.edu.iff.nsi.services.imageSearch;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.*;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.resource.ClientResource;

public class ServiceTest {
    @Test
    public void works() throws Exception {
        assertThat(new ClientResource("http://localhost:8111/").get().getText(),
                is(equalTo("Hello world!")));
    }

    private Server server;

    @Before
    public void serverUp() throws Exception {
        server = new Server(Protocol.HTTP, 8111, ImageSearchService.class);
        server.start();
    }

    @After
    public void serverDown() throws Exception {
        server.stop();
    }
}