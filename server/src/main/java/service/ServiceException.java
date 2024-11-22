package service;

public class ServiceException extends Exception {
    Type type;

    ServiceException(String message, Type type) {
        super(message);
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
            BADINPUT,
            BADAUTH,
            INPUTTAKEN,
    }
}
