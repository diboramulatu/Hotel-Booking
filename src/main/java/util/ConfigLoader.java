package service;

import dao.BookingDAO;
import dao.RoomDAO;
import exception.*;
import model.Booking;

import java.time.LocalDate;
import java.util.Scanner;

public class ServiceDemo {
    public static void main(String[] args) {
        RoomService roomService = new RoomService(new RoomDAO());
        BookingService bookingService = new BookingService(new BookingDAO(), roomService);
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Enter customer ID: ");
            int customerId = sc.nextInt();
            System.out.print("Enter room ID: ");
            int roomId = sc.nextInt();

            LocalDate checkIn = LocalDate.now().plusDays(1);
            LocalDate checkOut = LocalDate.now().plusDays(3);

            Booking booking = bookingService.createBooking(customerId, roomId, checkIn, checkOut);
            System.out.println("✅ Booking successful: ID = " + booking.getBookingId());

            double cost = bookingService.calculateCost(booking, 100.0); // test with 100 birr/day
            System.out.println("💰 Total cost: " + cost + " birr");

            System.out.print("Cancel booking? (yes/no): ");
            String confirm = sc.next();
            if (confirm.equalsIgnoreCase("yes")) {
                bookingService.cancelBooking(booking.getBookingId(), roomId);
                System.out.println("❌ Booking cancelled.");
            }

        } catch (InvalidBookingException | RoomUnavailableException e) {
            System.out.println("❗ Input error: " + e.getMessage());
        } catch (ServiceException e) {
            System.out.println("⚠️ Service error: " + e.getMessage());
        }
    }
}