package ui;

import model.*;
import service.*;
import exception.*;
import dao.*;

import java.time.LocalDate;
import java.util.*;

public class MainMenu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Initialize DAOs and Services
        RoomDAO roomDAO = new RoomDAO();
        BookingDAO bookingDAO = new BookingDAO();
        RoomService roomService = new RoomService(roomDAO);
        BookingService bookingService = new BookingService(bookingDAO, roomService);

        Room room1 = new SingleRoom(4, 100.0, "103");
        Room room2 = new DoubleRoom(2, 150.0,"104");
        Customer customer = new Customer();

        System.out.println("=== Welcome to the Hotel Booking System ===");

        while (true) {
            customer.showMenu();
            int choice = scanner.nextInt();

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Check-in date (YYYY-MM-DD): ");
                        LocalDate in = LocalDate.parse(scanner.next());
                        System.out.print("Check-out date (YYYY-MM-DD): ");
                        LocalDate out = LocalDate.parse(scanner.next());

                        Booking booking = bookingService.createBooking(1, room1.getRoomId(), in, out);
                        System.out.println("Booking successful: ID = " + booking.getBookingId());
                        break;

                    case 2:
                        System.out.print("Booking ID to cancel: ");
                        int id = scanner.nextInt();
                        bookingService.cancelBooking(id, room1.getRoomId());
                        System.out.println("Booking cancelled.");
                        break;

                    case 3:
                        System.out.println("Goodbye!");
                        return;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
