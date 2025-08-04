package service;

import dao.BookingDAO;
import exception.*;
import model.Booking;

import service.RoomService;
import java.time.LocalDate;
import java.util.List;
import util.FileLogger;

import service.validation.InputValidator;
import service.rules.BookingRules;

public class BookingService {
    private final BookingDAO bookingDAO;
    private final RoomService roomService;

    public BookingService(BookingDAO bookingDAO, RoomService roomService) {
        this.bookingDAO = bookingDAO;
        this.roomService = roomService;
    }

    public Booking createBooking(int customerId, int roomId, LocalDate checkIn, LocalDate checkOut)
        throws InvalidBookingException, RoomUnavailableException, ServiceException {

        try {
            //Input validation logic for customer and room ID, and date rules
            InputValidator.requirePositiveId(customerId, "Customer ID");
            InputValidator.requirePositiveId(roomId, "Room ID");
            BookingRules.ensureValidDates(checkIn, checkOut);
        } catch (IllegalArgumentException e) {
            throw new InvalidBookingException(e.getMessage());
        }

        if (!bookingDAO.isRoomAvailable(roomId, checkIn, checkOut)) {
            throw new RoomUnavailableException("Room " + roomId + " is not available.");
        }

        try {
             //Successfully handles booking creation and logs activity
            Booking booking = new Booking(customerId, roomId, checkIn, checkOut);
            bookingDAO.addBooking(booking);
            FileLogger.info("Booking created: CustomerID = " + customerId + ", RoomID = " + roomId + ", CheckIn = " + checkIn + ", CheckOut = " + checkOut);
            return booking;
        } catch (Exception e) {
            throw new ServiceException("Failed to create booking.", e);
        }
    }

    public void cancelBooking(int bookingId, int roomId) throws ServiceException {
        try {
            InputValidator.requirePositiveId(bookingId, "Booking ID");
            InputValidator.requirePositiveId(roomId, "Room ID");

            bookingDAO.cancelBooking(bookingId);
            roomService.markReleased(roomId);
            FileLogger.info("Booking cancelled: BookingID = " + bookingId + ", RoomID = " + roomId);
        } catch (Exception e) {
            throw new ServiceException("Failed to cancel booking.", e);
        }
    }

    public List<Booking> getBookingsByCustomer(int customerId) throws ServiceException {
        try {
            InputValidator.requirePositiveId(customerId, "Customer ID");
            return bookingDAO.getBookingsByCustomer(customerId);
        } catch (Exception e) {
            throw new ServiceException("Failed to get bookings.", e);
        }
    }

    public double calculateCost(Booking booking, double dailyRate) {
        if (booking == null)
            throw new IllegalArgumentException("Booking cannot be null.");
        if (dailyRate < 0)
            throw new IllegalArgumentException("Daily rate must be non-negative.");

        return booking.calculateCost(dailyRate);
    }
}
