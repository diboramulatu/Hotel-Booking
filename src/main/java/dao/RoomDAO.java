// RoomDAO.java
package dao;
import model.SingleRoom;
import model.DoubleRoom;
import model.Room;
import java.time.LocalDate;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    public List<Room> getAllAvailableRooms(LocalDate checkIn, LocalDate checkOut) {
        List<Room> rooms = new ArrayList<>();
    
        String sql = "SELECT * FROM room WHERE id NOT IN (" +
                     "    SELECT roomId FROM Booking " +
                     "    WHERE NOT (checkOutDate <= ? OR checkInDate >= ?)" +
                     ")";
    
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setString(1, checkIn.toString());
            stmt.setString(2, checkOut.toString());
    
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Room room = new Room(
                        rs.getInt("id"),
                        rs.getString("roomType"),
                        rs.getDouble("price"),
                        rs.getString("roomNumber")
                    );
                    rooms.add(room);
                }
            }
    
        } catch (SQLException e) {
            System.out.println("Error fetching available rooms: " + e.getMessage());
        }
    
        return rooms;
    }
    
    public void updateRoomAvailability(int roomId, boolean isAvailable) {
        String sql = "UPDATE room SET isAvailable = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, isAvailable);
            stmt.setInt(2, roomId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating room: " + e.getMessage());
        }
    }
    public void addRoom(Room room) {
        String sql = "INSERT INTO Room (roomNumber, roomType, price) VALUES (?, ?, ?)";
    
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setString(1, room.getRoomNumber());
            stmt.setString(2, room instanceof SingleRoom ? "single" : "double");
            stmt.setDouble(3, room.getPrice());
    
            stmt.executeUpdate();
            System.out.println("Room added: " + room.getRoomNumber());
    
        } catch (SQLException e) {
            System.out.println("Error inserting room: " + e.getMessage());
        }
    }
    
    
    
    public Room getRoomById(int roomId) {
        String sql = "SELECT * FROM room WHERE id = ?";
    
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setInt(1, roomId);
    
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String roomType = rs.getString("roomType");
                    double price = rs.getDouble("price");
                    String roomNumber = rs.getString("roomNumber");
    
                    switch (roomType.toLowerCase()) {
                        case "single":
                            return new SingleRoom(id, price, roomNumber);
                        case "double":
                            return new DoubleRoom(id, price, roomNumber);
                        default:
                            System.out.println("Unknown room type: " + roomType);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching room: " + e.getMessage());
        }
    
        return null;
    }
    
    
}

