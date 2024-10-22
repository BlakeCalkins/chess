package service;

import dataaccess.DataAccessException;
import model.AuthData;

import java.util.Objects;

public class LogoutService {
    LogoutRequest request;

    public LogoutService(LogoutRequest request) {
        this.request = request;
    }

    public LogoutResult logoutUser() throws DataAccessException {
        AuthData authData = ServiceUtils.getAuth(request.authToken());
        if (authData == null || !Objects.equals(authData.authToken(), request.authToken())) {
            return new LogoutResult("Not authorized");
        }
        ServiceUtils.deleteAuth(request.authToken());
        return new LogoutResult("");
    }
}
