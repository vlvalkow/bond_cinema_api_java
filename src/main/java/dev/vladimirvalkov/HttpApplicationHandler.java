package dev.vladimirvalkov;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dev.vladimirvalkov.app.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;

public class HttpApplicationHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Request request = new Request(
                exchange.getRequestMethod(),
                exchange.getRequestURI(),
                exchange.getRequestHeaders(),
                exchange.getRequestBody()
        );

        Kernel kernel = new Kernel(
                new Router(new Route[]{
                        new Route("index", "GET", "/"),
                        new Route("getBookings", "GET", "/bookings")
                }),
                new Controller()
        );

        Response response = kernel.handleRequest(request);
        sendResponse(response, exchange);

        exchange.close();
    }

    private void sendResponse(Response response, HttpExchange exchange) throws IOException {
        for (Map.Entry<String, String> header : response.headers().entrySet()) {
            exchange.getResponseHeaders().add(header.getKey(), header.getValue());
        }

        exchange.sendResponseHeaders(response.code(), response.content().getBytes().length);

        OutputStream output = exchange.getResponseBody();
        output.write(response.content().getBytes());
        output.flush();
    }
}
