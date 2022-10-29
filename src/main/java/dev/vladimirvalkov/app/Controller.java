package dev.vladimirvalkov.app;

public class Controller {
    public Response index() {
        return new Response(200, "Hello World!");
    }

    public Response getBookings(Request request) {
        return new Response(200, request.uri().toString());
    }
}
