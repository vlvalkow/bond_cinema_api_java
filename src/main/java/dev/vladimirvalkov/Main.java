package dev.vladimirvalkov;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/", new HttpApplicationHandler());

        server.setExecutor(null); // creates a default executor
        server.start();
    }
}
