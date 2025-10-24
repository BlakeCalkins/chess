package service;

import dataaccess.DataAccess;
import datamodel.*;

public class Service {
    private final DataAccess dataAccess;

    public Service(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public AuthData register(UserData user) throws Exception{
        if ((user.email() == null) || (user.password() == null) || (user.username() == null)) {
            throw new BadRequestException("bad request");
        }
        if (dataAccess.getUser(user.username()) != null) {
            throw new AlreadyTakenException("already exists");
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
