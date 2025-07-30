package service.validation;

public final class InputValidator {
    private InputValidator() {}

    public static void requirePositiveId(int id, String label) {
        if (id <= 0) throw new IllegalArgumentException(label + " must be a positive number.");
    }
}