package service;

import dataaccess.AuthDataAccess;
import dataaccess.UserDataAccess;
import datamodel.*;
import java.util.UUID;

public class Service {
    private final UserDataAccess userDataAccess;
    private final AuthDataAccess authDataAccess;

    public Service(UserDataAccess userDataAccess, AuthDataAccess authDataAccess) {
        this.userDataAccess = userDataAccess;
        this.authDataAccess = authDataAccess;
    }

    public void clear() {
        userDataAccess.clear();
        authDataAccess.clear();
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
        if (user.username() == null || user.password() == null) {
            throw new BadRequestException("bad request");
        }
        if (userDataAccess.getUser(user.username()) == null) {
            throw new UnauthorizedException("no user exists.");
        }
        if (!userDataAccess.validPassword(user)) {
            throw new UnauthorizedException("unauthorized");
        }

        return new AuthData(user.username(), generateAuthToken());
    }

    public void logout(String authToken) throws Exception {
        if (!authDataAccess.verifyAuth(authToken)) {
            throw new UnauthorizedException("unauthorized");
        }
        authDataAccess.deleteAuth(authToken);
    }

    // stub


    public String generateAuthToken() {
        String authToken = UUID.randomUUID().toString();
        this.authDataAccess.add(authToken);
        return authToken;
    }
}
