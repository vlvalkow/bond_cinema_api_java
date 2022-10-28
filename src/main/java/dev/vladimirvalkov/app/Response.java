package dev.vladimirvalkov.app;

public record Response(
        int code,
        String content,
        String[] headers
) {

    public Response(int code, String content) {
        this(code, content, null);
    }
}
