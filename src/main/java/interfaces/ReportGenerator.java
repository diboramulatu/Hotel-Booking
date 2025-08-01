package interfaces;

import model.Booking;
import java.util.List;

public interface ReportGenerator {
    String generateBookingReport(List<Booking> bookings);
    String generateRoomOccupancyReport();
    String generateRevenueReport();
}
