package lab1;

/**
 * Exception for subtraction when first operand is less that second.
 */
public class SubtractionException extends Exception {

    SubtractionException(String message) {
        super(message);
    }

    SubtractionException(LongNumber A, LongNumber B) {
        super("Subtraction Error: " + A.hex() + " is less than " + B.hex());
    }
}
