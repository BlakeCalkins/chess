package service;

import dataaccess.DataAccessException;
import dataaccess.UserDataAccess;
import datamodel.*;

public class Service {
    private final UserDataAccess userDataAccess;

    public Service(UserDataAccess userDataAccess) {
        this.userDataAccess = userDataAccess;
    }

    public AuthData register(UserData user) throws Exception{
        if ((user.email() == null) || (user.password() == null) || (user.username() == null)) {
            throw new BadRequestException("bad request");
        }
        if (userDataAccess.getUser(user.username()) != null) {
            throw new AlreadyTakenException("already exists");
        }
        userDataAccess.createUser(user);
        return new AuthData(user.username(), generateAuthToken());
    }

    public AuthData login(UserData user) throws Exception {
//        if (user.username() == null || user.email() == null || user.password() == null) {
//            throw new BadRequestException("bad request");
//        }
        if (user.username() == null) {
            throw new BadRequestException("bad request");
        }
        if (userDataAccess.getUser(user.username()) == null) {
            throw new UnauthorizedException("no user exists.");
        }
        if (!userDataAccess.validPassword(user)) {
            throw new BadRequestException("unauthorized");
        }

        return new AuthData(user.username(), generateAuthToken());
    }

    // stub
    private String generateAuthToken() {
        return "xyz";
    }
}
