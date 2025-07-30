package model;

public class Customer extends User {
    
    public Customer() {
        // Default constructor
    }
    
    public Customer(int userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }
    
    @Override
    public void showMenu() {
        System.out.println("Customer Menu: 1. Book Room 2. Cancel Booking 3. Logout");
    }
    
    @Override
    public String toString() {
        return "Customer{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
