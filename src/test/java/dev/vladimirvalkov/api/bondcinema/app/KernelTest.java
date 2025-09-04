package dev.vladimirvalkov.api.bondcinema.app;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class KernelTest {
    private Request request;

    @BeforeEach
    void setUp() {
        try {
        this.request = new Request("GET", new URI("/"), null, null);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void whenVisitTheIndexRoute_shouldReceiveSuccessResponse() {
        Kernel kernel = new Kernel(new Router(new Route[]{new Route("index", "GET", "/")}), new Controller());
        Response response = kernel.handleRequest(this.request);

        assertEquals("{\"message\":\"Hello World\"}", response.content());
    }
}
