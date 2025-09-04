package dev.vladimirvalkov.api.bondcinema.app;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.HashMap;

public class Controller {
    private final Database db;

    public Controller() {
        this.db = null;
    }

    public Controller(Database db) {
        this.db = db;
    }

    public Response index(Request request) {
        HashMap<String, String> source = new HashMap<>();
        source.put("message", "Welcome to Bond Cinema!");
        JSONObject jsonObject = new JSONObject(source);

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-type", "application/json");

        return new Response(200, jsonObject.toString(), headers);
    }

    public Response getBookings(Request request) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-type", "application/json");

        if (this.db == null) {
            return new Response(200, "[]", headers);
        }

        try {
            BookingDao dao = new BookingDao(this.db);
            var bookings = dao.listBookings();
            JSONArray items = new JSONArray();
            for (var b : bookings) {
                JSONObject o = new JSONObject();
                o.put("id", b.id());
                o.put("customer_name", b.customerName());
                o.put("movie", b.movie());
                o.put("seats", b.seats());
                if (b.createdAt() != null) {
                    o.put("created_at", b.createdAt().toString());
                }
                items.put(o);
            }
            return new Response(200, items.toString(), headers);
        } catch (SQLException e) {
            String body = new JSONObject(new HashMap<String, String>() {{ put("error", e.getMessage()); }}).toString();
            return new Response(500, body, headers);
        }
    }

    public Response healthcheck(Request request) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-type", "application/json");

        if (this.db == null) {
            return new Response(200, new JSONObject(new HashMap<String, String>() {{ put("status", "degraded"); }}).toString(), headers);
        }

        try (var conn = db.getConnection(); var stmt = conn.createStatement(); var rs = stmt.executeQuery("SELECT 1")) {
            boolean ok = rs.next();
            String body = new JSONObject(new HashMap<String, String>() {{ put("database", ok ? "ok" : "fail"); }}).toString();
            return new Response(ok ? 200 : 500, body, headers);
        } catch (SQLException e) {
            String body = new JSONObject(new HashMap<String, String>() {{ put("database", "fail"); put("error", e.getMessage()); }}).toString();
            return new Response(500, body, headers);
        }
    }
}
