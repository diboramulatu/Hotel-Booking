package model;

public class Admin extends User {
    
    public Admin() {
        // Default constructor
    }
    
    public Admin(int userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }
    
    @Override
    public void showMenu() {
        System.out.println("Admin Menu: 1. View Bookings 2. Manage Rooms 3. Logout");
    }
    
    @Override
    public String toString() {
        return "Admin{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
