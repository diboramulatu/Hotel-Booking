package ui;

import dao.CustomerDAO;
import dao.DatabaseInitializer;
import model.Customer;
import model.Room;
import java.util.List;
import dao.RoomDAO;
import model.SingleRoom;
import model.DoubleRoom;
public class DatabaseTest {
    public static void main(String[] args) {
        // Step 1: Create DB and tables
        System.out.println("Data base initialized");
        DatabaseInitializer.initialize();

        // Step 2: Insert a test customer
        // Customer testCustomer = new Customer();
        // testCustomer.name = "Alice Wonderland";
        // testCustomer.email = "alice@example.com";

        // CustomerDAO customerDAO = new CustomerDAO();
        // customerDAO.addCustomer(testCustomer);

        
        Room room1 = new SingleRoom(1, 100.0,"101");
        Room room2 = new DoubleRoom(2, 150.0, "202");
        RoomDAO roomDAO = new RoomDAO();
        roomDAO.addRoom(room1);  // You need to implement this method if not already present
        roomDAO.addRoom(room2);

        // Step 3: Retrieve and print all customers
        // List<Customer> allCustomers = customerDAO.getAllCustomers();
        // System.out.println("=== All Customers ===");
        // for (Customer c : allCustomers) {
        //     System.out.println("ID: " + c.userId + ", Name: " + c.name + ", Email: " + c.email);
        // }
    }
}
