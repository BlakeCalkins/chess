package exception;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResponseException extends Exception {

    public enum Code {
        ServerError,
        BadRequest,
        Unauthorized,
        Forbidden
    }

    final private Code code;

    public ResponseException(Code code, String message) {
        super(message);
        this.code = code;
    }

    public String parseMessage(String message) {
        Pattern pattern = Pattern.compile("Error: (.*?)\"");
        Matcher matcher = pattern.matcher(message);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "";
        }
    }

    public Code code() {
        return code;
    }

    public static Code fromHttpStatusCode(int httpStatusCode) {
        return switch (httpStatusCode) {
            case 500 -> Code.ServerError;
            case 400 -> Code.BadRequest;
            case 401 -> Code.Unauthorized;
            case 403 -> Code.Forbidden;
            default -> throw new IllegalArgumentException("Unknown HTTP status code: " + httpStatusCode);
        };
    }

}