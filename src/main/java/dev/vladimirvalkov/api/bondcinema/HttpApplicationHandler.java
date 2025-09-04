package dev.vladimirvalkov.api.bondcinema;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dev.vladimirvalkov.api.bondcinema.app.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class HttpApplicationHandler implements HttpHandler {
    private final Controller controller;

    public HttpApplicationHandler() {
        DatabaseConfig config = DatabaseConfig.fromEnv();

        if (config.url() != null) {
            // Run migrations once on startup
            DatabaseMigrator.migrate(config);
            this.controller = new Controller(new Database(config.url(), config.user(), config.pass()));
        } else {
            this.controller = new Controller();
        }
    }
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
                        new Route("getBookings", "GET", "/bookings"),
                        new Route("healthcheck", "GET", "/healthcheck")
                }),
        this.controller
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
