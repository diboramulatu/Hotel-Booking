package ui;

import dao.DatabaseInitializer;

public class Main {
    public static void main(String[] args) {
        // Initialize database tables (Room, Customer, Booking)
        DatabaseInitializer.initialize();

        // Launch the application
        //MainMenu.main(args); // CLI menu
        // OR use GUI instead:
        new RoomBookingGUI();
    }
}
