// CustomerDAO.java

package dao;

import model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    public void addCustomer(Customer c) {
        String sql = "INSERT INTO Customer (name, email) VALUES (?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, c.name);
            stmt.setString(2, c.email);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding customer: " + e.getMessage());
        }
    }
    public Customer getCustomerById(int customerId) {
        String sql = "SELECT * FROM customers WHERE id = ?";
    
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setInt(1, customerId);
    
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Customer customer = new Customer();
                    customer.userId = rs.getInt("id");
                    customer.name = rs.getString("name");
                    customer.email = rs.getString("email");
                    return customer;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving customer by ID: " + e.getMessage());
        }
    
        return null;
    }
    public void updateCustomer(Customer customer) {
        String sql = "UPDATE customers SET name = ?, email = ? WHERE id = ?";
    
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setString(1, customer.name);
            stmt.setString(2, customer.email);
            stmt.setInt(3, customer.userId); // assuming 'userId' is the primary key
    
            int rows = stmt.executeUpdate();
    
            if (rows > 0) {
                System.out.println("✅ Customer updated successfully: " + customer);
            } else {
                System.out.println("⚠️ No customer found with ID: " + customer.userId);
            }
    
        } catch (SQLException e) {
            System.out.println("❌ Error updating customer: " + e.getMessage());
        }
    }
        
    public List<Customer> getAllCustomers() {
        List<Customer> Customer = new ArrayList<>();
        String sql = "SELECT * FROM Customer";

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Customer c = new Customer();
                c.userId = rs.getInt("id");
                c.name = rs.getString("name");
                c.email = rs.getString("email");
                Customer.add(c);
            }

        } catch (SQLException e) {
            System.out.println("Error loading Customer: " + e.getMessage());
        }

        return Customer;
    }

    public void deleteCustomer(int customerId) {
        String sql = "DELETE FROM customers WHERE id = ?";
    
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setInt(1, customerId);
    
            int rows = stmt.executeUpdate();
    
            if (rows > 0) {
                System.out.println("✅ Customer deleted: " + customerId);
            } else {
                System.out.println("⚠️ No customer found with ID: " + customerId);
            }
    
        } catch (SQLException e) {
            System.out.println("❌ Error deleting customer: " + e.getMessage());
        }
    }
    
}
