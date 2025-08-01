package ui;

import service.RoomService;
import dao.RoomDAO;
import model.Room;
import model.Booking;
import service.BookingService;
import dao.BookingDAO;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

public class RoomBookingGUI extends JFrame {

    private JComboBox<Room> roomComboBox;
    private JSpinner checkInSpinner;
    private JSpinner checkOutSpinner;
    private JLabel totalLabel;
    private JTextField customerIdField;
    private JTextField cancelBookingIdField;

    private BookingService bookingService = new BookingService(new BookingDAO(), new RoomService(new RoomDAO()));

    public RoomBookingGUI() {
        setTitle("Hotel Room Booking");
        setSize(600, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 2, 10, 10));

        RoomDAO roomDAO = new RoomDAO();
        LocalDate today = LocalDate.now();
        LocalDate later = today.plusDays(3);

        List<Room> availableRooms = roomDAO.getAllAvailableRooms(today, later);

        // UI Components
        add(new JLabel("Customer ID:"));
        customerIdField = new JTextField();
        add(customerIdField);

        add(new JLabel("Select Room:"));
        roomComboBox = new JComboBox<>(availableRooms.toArray(new Room[0]));
        add(roomComboBox);

        add(new JLabel("Check-in Date:"));
        checkInSpinner = new JSpinner(new SpinnerDateModel());
        add(checkInSpinner);

        add(new JLabel("Check-out Date:"));
        checkOutSpinner = new JSpinner(new SpinnerDateModel());
        add(checkOutSpinner);

        add(new JLabel("Total Price:"));
        totalLabel = new JLabel("0.00");
        add(totalLabel);

        // Calculate total button
        JButton calcButton = new JButton("Calculate Total");
        calcButton.addActionListener(e -> calculateTotal());
        add(calcButton);

        // Book button
        JButton bookButton = new JButton("Book Room");
        bookButton.addActionListener(e -> bookRoom());
        add(bookButton);

        add(new JLabel("Booking ID to Cancel:"));
        cancelBookingIdField = new JTextField();
        add(cancelBookingIdField);

        JButton cancelButton = new JButton("Cancel Booking");
        cancelButton.addActionListener(e -> {
            String bookingIdText = cancelBookingIdField.getText().trim();
            if (bookingIdText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a Booking ID to cancel.");
                return;
            }
            try {
                int bookingId = Integer.parseInt(bookingIdText);
                int customerId = Integer.parseInt(customerIdField.getText().trim());
                bookingService.cancelBooking(customerId, bookingId);
                JOptionPane.showMessageDialog(this, "Booking cancelled successfully.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Cancellation failed: " + ex.getMessage());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
        add(cancelButton);

        setVisible(true);
    }

    private void calculateTotal() {
        try {
            Date checkInDate = (Date) checkInSpinner.getValue();
            Date checkOutDate = (Date) checkOutSpinner.getValue();
            LocalDate in = checkInDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate out = checkOutDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            long days = ChronoUnit.DAYS.between(in, out);
            Room selectedRoom = (Room) roomComboBox.getSelectedItem();
            double price = selectedRoom.getPrice() * days;
            totalLabel.setText(String.format("%.2f", price));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error calculating total: " + e.getMessage());
        }
    }

    private void bookRoom() {
        try {
            int customerId = Integer.parseInt(customerIdField.getText().trim());
            Room selectedRoom = (Room) roomComboBox.getSelectedItem();
            Date checkInDate = (Date) checkInSpinner.getValue();
            Date checkOutDate = (Date) checkOutSpinner.getValue();
            LocalDate in = checkInDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate out = checkOutDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            Booking booking = bookingService.createBooking(customerId, selectedRoom.getRoomId(), in, out);
            JOptionPane.showMessageDialog(this, "Booking successful! Booking ID: " + booking.getBookingId());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Booking failed: " + e.getMessage());
        }
    }
}
