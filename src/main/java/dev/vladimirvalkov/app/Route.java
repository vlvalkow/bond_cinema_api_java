package dev.vladimirvalkov.app;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Route {
    private final String name;
    private final String httpMethod;
    private final String urlPattern;

    public Route(String name, String httpMethod, String urlPattern) {
        this.name = name;
        this.httpMethod = httpMethod;
        this.urlPattern = urlPattern;
    }

    public boolean match(Request request) {

        if (!this.httpMethod.equals(request.method())) {
            return false;
        }

        Pattern pattern = Pattern.compile(this.urlPattern);
        Matcher matcher = pattern.matcher(request.uri().toString());

        return matcher.find();
    }

    public String getName() {
        return name;
    }
}
