import dao.RoomDAO;
import model.Room;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class RoomBookingGUI extends JFrame {

    private JComboBox<Room> roomComboBox;
    private JSpinner checkInSpinner;
    private JSpinner checkOutSpinner;
    private JLabel totalLabel;

    public RoomBookingGUI() {
        setTitle("Hotel Room Booking");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 10, 10));

        // Load rooms from DB
        RoomDAO roomDAO = new RoomDAO();
        List<Room> availableRooms = roomDAO.getAllAvailableRooms();

        // Room selection
        add(new JLabel("Select Room:"));
        roomComboBox = new JComboBox<>(availableRooms.toArray(new Room[0]));
        add(roomComboBox);

        // Check-in date
        add(new JLabel("Check-in Date:"));
        checkInSpinner = new JSpinner(new SpinnerDateModel());
        add(checkInSpinner);

        // Check-out date
        add(new JLabel("Check-out Date:"));
        checkOutSpinner = new JSpinner(new SpinnerDateModel());
        add(checkOutSpinner);

        // Total price
        add(new JLabel("Total Price:"));
        totalLabel = new JLabel("0.00");
        add(totalLabel);

        // Button to calculate price
        JButton calcButton = new JButton("Calculate Total");
        calcButton.addActionListener(e -> calculateTotal());
        add(calcButton);

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RoomBookingGUI::new);
    }
}
