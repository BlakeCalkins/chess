package service;

import dataaccess.*;
import model.AuthData;
import model.UserData;
import java.util.UUID;

public final class ServiceUtils {

    private static final AuthDAO authDAO = new MemoryAuthDAO();
    private static final GameDAO gameDAO = new MemoryGameDAO();
    private static final UserDAO userDAO = new MemoryUserDAO();

    private ServiceUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

    public static UserData getUser(String username) throws DataAccessException {
        return userDAO.getUser(username);
    }

    public static void createUser(UserData data) throws DataAccessException {
        userDAO.createUser(data);
    }

    public static void createAuth(AuthData data) throws DataAccessException {
        authDAO.createAuth(data);
    }

//    // Static utility method
//    public static void log(String message) {
//        System.out.println("Log: " + message);
//    }
//
//    // Another static utility method
//    public static int add(int a, int b) {
//        return a + b;
//    }
}

