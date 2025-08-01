package service.rules;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public final class BookingRules {
    private static final int MAX_STAY_DAYS = 30;

    private BookingRules() {}

    public static void ensureValidDates(LocalDate in, LocalDate out) {
        if (in == null || out == null)
            throw new IllegalArgumentException("Dates cannot be null.");
        if (!out.isAfter(in))
            throw new IllegalArgumentException("Checkout must be after check-in.");
        if (in.isBefore(LocalDate.now()))
            throw new IllegalArgumentException("Check-in cannot be in the past.");
        if (ChronoUnit.DAYS.between(in, out) > MAX_STAY_DAYS)
            throw new IllegalArgumentException("Stay exceeds maximum allowed days (" + MAX_STAY_DAYS + ")");
    }
}