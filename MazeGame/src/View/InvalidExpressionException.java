package View;

/**
 * RuntimeException for when the player inputs an invalid Expression (not W, A, S, D, ?, C or M)
 */
public class InvalidExpressionException extends RuntimeException {
    public InvalidExpressionException(String message) {
        super(message);
    }
}
