package dev.vladimirvalkov.api.bondcinema.app;

import java.util.HashMap;

public record Response(
        int code,
        String content,
        HashMap<String, String> headers
) {

    public Response(int code, String content) {
        this(code, content, new HashMap<>());
    }
}
