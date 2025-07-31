import dao.RoomDAO;
import model.Room;
import model.Booking;
import service.BookingService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class RoomBookingGUI extends JFrame {

    private JComboBox<Room> roomComboBox;
    private JSpinner checkInSpinner;
    private JSpinner checkOutSpinner;
    private JLabel totalLabel;
    private JTextField customerIdField;
    private JTextField cancelBookingIdField;

    private BookingService bookingService = new BookingService(new BookingDAO(), new RoomService());

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

        // Book Room button
        JButton bookButton = new JButton("Book Room");
        bookButton.addActionListener(e -> bookRoom());
        add(bookButton);

        // Cancel Booking Section
        add(new JLabel("Booking ID to Cancel:"));
        cancelBookingIdField = new JTextField();
        add(cancelBookingIdField);

        JButton cancelButton = new JButton("Cancel Booking");
        cancelButton.addActionListener(e -> cancelBooking());
        add(cancelButton);

        setVisible(true);
    }

    private void calculateTotal() {
        Room selectedRoom = (Room) roomComboBox.getSelectedItem();
        if (selectedRoom == null) {
            totalLabel.setText("Select a room");
            return;
        }

        LocalDate checkIn = ((java.util.Date) checkInSpinner.getValue()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        LocalDate checkOut = ((java.util.Date) checkOutSpinner.getValue()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();

        long days = ChronoUnit.DAYS.between(checkIn, checkOut);
        if (days <= 0) {
            totalLabel.setText("Invalid dates");
            return;
        }

        double totalPrice = selectedRoom.getPrice() * days;
        totalLabel.setText(String.format("%.2f", totalPrice));
    }

    private void bookRoom() {
        try {
            int customerId = Integer.parseInt(customerIdField.getText());            Room selectedRoom = (Room) roomComboBox.getSelectedItem();
            LocalDate checkIn = ((java.util.Date) checkInSpinner.getValue()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
            LocalDate checkOut = ((java.util.Date) checkOutSpinner.getValue()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();

            Booking booking = bookingService.createBooking(customerId, selectedRoom.getRoomId(), checkIn, checkOut);
            JOptionPane.showMessageDialog(this, "Booking successful! Booking ID: " + booking.getRoomId());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void cancelBooking() {
        try {
            int bookingId = Integer.parseInt(cancelBookingIdField.getText());
            bookingService.cancelBooking(customerId, bookingId);

            JOptionPane.showMessageDialog(this, "Booking cancelled successfully.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Cancellation failed: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RoomBookingGUI::new);
    }
}
