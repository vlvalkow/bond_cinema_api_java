package dev.vladimirvalkov.app;

public class Router {
    private final Route[] routes;

    public Router(Route[] routes) {
        this.routes = routes;
    }

    public Route route(Request request) {
        for (Route route : this.routes) {
            if (route.match(request)) {
                return route;
            }
        }

        return null;
    }
}
