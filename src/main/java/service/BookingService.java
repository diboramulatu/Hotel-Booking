package service;

import dao.BookingDAO;
import exception.*;
import model.Booking;

import java.time.LocalDate;
import java.util.List;

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
            InputValidator.requirePositiveId(customerId, "Customer ID");
            InputValidator.requirePositiveId(roomId, "Room ID");
            BookingRules.ensureValidDates(checkIn, checkOut);
        } catch (IllegalArgumentException e) {
            throw new InvalidBookingException(e.getMessage());
        }