package service;

public class BadRequestException extends Exception {
    private final String message;

    public BadRequestException(String message) {

        super(message);
        this.message = message;
    }

    public String getMessage() {
        return "{\"message\": \"Error: " + this.message + "\"}";
    }

    public int getCode() {
        return 400;
    }
}
