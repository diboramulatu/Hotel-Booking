// BookingDAO.java
package dao;

import model.Booking;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public void addBooking(Booking b) {
        String sql = "INSERT INTO Booking (customerId, roomId, checkInDate, checkOutDate) VALUES (?, ?, ?, ?)";
    
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
    
            stmt.setInt(1, b.getCustomerId());
            stmt.setInt(2, b.getRoomId());
            stmt.setString(3, b.getCheckInDate().toString());
            stmt.setString(4, b.getCheckOutDate().toString());
    
            int affectedRows = stmt.executeUpdate();
    
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        b.setBookingId(generatedId);  // Set it into the Booking object
                    }
                }
            }
    
        } catch (SQLException e) {
            System.out.println("Error adding booking: " + e.getMessage());
        }
    }    

    public List<Booking> getBookingsByCustomer(int customerId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM Booking WHERE customerId = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Booking b = new Booking(
                    rs.getInt("customerId"),
                    rs.getInt("roomId"),
                    LocalDate.parse(rs.getString("checkInDate")),
                    LocalDate.parse(rs.getString("checkOutDate"))
                );
                bookings.add(b);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving bookings: " + e.getMessage());
        }

        return bookings;
    }
    public void cancelBooking(int bookingId) {
        String sql = "DELETE FROM Booking WHERE id = ?";
    
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setInt(1, bookingId);
    
            int rows = stmt.executeUpdate();
    
            if (rows > 0) {
                System.out.println("✅ Booking cancelled: ID = " + bookingId);
            } else {
                System.out.println("⚠️ No booking found with ID: " + bookingId);
            }
    
        } catch (SQLException e) {
            System.out.println("❌ Error cancelling booking: " + e.getMessage());
        }
    }

    public boolean isRoomAvailable(int roomId, LocalDate checkIn, LocalDate checkOut) {
        System.out.println(roomId);
        String sql = "SELECT COUNT(*) FROM Booking " +
                     "WHERE roomId = ? " +
                     "AND NOT (checkOutDate <= ? OR checkInDate >= ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, roomId);
            stmt.setString(2, checkIn.toString());  // Dates must not overlap
            stmt.setString(3, checkOut.toString());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count == 0; // If count is 0, no overlap => available
                }
            }

        } catch (SQLException e) {
            System.out.println("Error checking room availability: " + e.getMessage());
        }

        return false; // If error, assume unavailable
    }
    
}
