package service;

public class UnauthorizedException extends Exception {
    public final String message;

    public UnauthorizedException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return "{\"message\": \"Error: " + this.message + "\"}";
    }

    public int getCode() {
        return 401;
    }
}
