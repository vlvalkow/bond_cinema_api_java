package dev.vladimirvalkov.api.bondcinema.app;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Kernel {
    private final Router router;
    private final Controller controller;

    public Kernel(Router router, Controller controller) {
        this.router = router;
        this.controller = controller;
    }

    public Response handleRequest(Request request) {
        Route route = this.router.route(request);

        if (null == route) {
            return new Response(404, "Not Found");
        }

        Method method;

        try {
            method = this.controller.getClass().getMethod(route.getName(), Request.class);
        } catch (NoSuchMethodException e) {
            return new Response(404, "Not Found");
        }

        try {
            return (Response) method.invoke(this.controller, request);
        } catch (IllegalAccessException e) {
            return new Response(500, "Server error: handler not accessible");
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            String msg = (cause != null ? cause.getClass().getSimpleName() + ": " + cause.getMessage() : "Unknown handler error");
            return new Response(500, "Server error: " + msg);
        }
    }
}
