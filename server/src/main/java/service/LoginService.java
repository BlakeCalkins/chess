package service;

import dataaccess.DataAccessException;
import model.AuthData;
import model.UserData;

import java.util.Objects;

public class LoginService {
    LoginRequest request;

    public LoginService(LoginRequest request) {
        this.request = request;
    }

    public LoginResult loginUser() throws DataAccessException {
        UserData userData = ServiceUtils.getUser(request.username());
        if (!Objects.equals(userData.password(), request.password())) {
            return new LoginResult("", "", "Access denied, wrong password.");
        }
        AuthData authData = new AuthData(ServiceUtils.generateToken(), request.username());
        ServiceUtils.createAuth(authData);
        return new LoginResult(authData.username(), authData.authToken(), "");
    }
}
