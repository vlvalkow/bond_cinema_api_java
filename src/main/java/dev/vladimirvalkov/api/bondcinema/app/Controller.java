package dev.vladimirvalkov.api.bondcinema.app;

import org.json.JSONObject;
import java.util.HashMap;

public class Controller {
    public Response index(Request request) {
        HashMap<String, String> source = new HashMap<>();
        source.put("message", "Hello World");
        JSONObject jsonObject = new JSONObject(source);

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-type", "application/json");

        return new Response(200, jsonObject.toString(), headers);
    }

    public Response getBookings(Request request) {
        return new Response(200, request.uri().toString());
    }
}
