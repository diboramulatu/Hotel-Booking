package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static final String DB_URL = "jdbc:sqlite:hotel.db";
    
    private DBUtil() {}
    
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(DB_URL);
        } catch (ClassNotFoundException e) {
            throw new SQLException("SQLite JDBC driver not found", e);
        }
    }
    
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}