package dev.vladimirvalkov.api.bondcinema.app;

import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class BookingDao {
    private final Database db;

    public BookingDao(Database db) {
        this.db = db;
    }

    public List<Booking> listBookings() throws SQLException {
        try (var conn = db.getConnection();
             var stmt = conn.createStatement();
             var rs = stmt.executeQuery("SELECT id, customer_name, movie, seats, created_at FROM bookings ORDER BY id DESC")) {
            List<Booking> bookings = new ArrayList<>();
            while (rs.next()) {
                Instant created = null;
                var ts = rs.getTimestamp("created_at");
                if (ts != null) created = ts.toInstant();
                bookings.add(new Booking(
                        rs.getInt("id"),
                        rs.getString("customer_name"),
                        rs.getString("movie"),
                        rs.getInt("seats"),
                        created
                ));
            }
            return bookings;
        }
    }
}
