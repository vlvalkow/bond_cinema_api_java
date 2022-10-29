package dev.vladimirvalkov.api.bondcinema.app;

import com.sun.net.httpserver.Headers;

import java.io.InputStream;
import java.net.URI;

public record Request(
        String method,
        URI uri,
        Headers headers,
        InputStream body
) {
}
