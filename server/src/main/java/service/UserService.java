package service;

import dataaccess.DataAccess;
import datamodel.*;

public class UserService {
    private final DataAccess dataAccess;

    public UserService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public AuthData register(UserData user) throws Exception{
        if (dataAccess.getUser(user.username()) != null) {
            throw new Exception("already exists"); // Check petshop for better exception
        }
        dataAccess.createUser(user);
        return new AuthData(user.username(), generateAuthToken());
    }

    public AuthData login(UserData user) throws Exception {
        if (dataAccess.getUser(user.username()) == null) {
            throw new Exception("no user exists.");
        }
        if (!dataAccess.validPassword(user)) {
            throw new Exception("unauthorized");
        }
        return new AuthData(user.username(), generateAuthToken());
    }

    // stub
    private String generateAuthToken() {
        return "xyz";
    }
}
