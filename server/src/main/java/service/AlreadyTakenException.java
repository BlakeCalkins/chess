package service;

public class AlreadyTakenException extends RuntimeException {
    private final String message;

    public AlreadyTakenException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return "{\"message\": \"Error: " + this.message + "\"}";
    }

    public int getCode() {
        return 403;
    }
}
