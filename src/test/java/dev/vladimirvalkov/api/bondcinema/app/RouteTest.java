package dev.vladimirvalkov.api.bondcinema.app;

import com.sun.net.httpserver.Headers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RouteTest {
    private Request request;

    @BeforeEach
    void setUp() {
        try {
            this.request = new Request("GET",
                    new URI("/foo"),
                    new Headers(),
                    new InputStream() {
                        @Override
                        public int read() {
                            return 0;
                        }
                    });
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void whenUrlPatternIsDifferent_shouldNotMatchRequest() {
        Route route = new Route("foo", "GET", "/");

        assertFalse(route.match(this.request));
    }

    @Test
    void whenUrlPatternIsTheSame_shouldMatchRequest() {
        Route route = new Route("foo", "GET", "/foo");

        assertTrue(route.match(this.request));
    }
}
