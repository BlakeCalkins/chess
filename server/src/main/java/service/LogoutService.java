package service;

import dataaccess.DataAccessException;
import model.AuthData;

public class LogoutService {
    LogoutRequest request;

    public LogoutService(LogoutRequest request) {
        this.request = request;
    }

    public LogoutResult logoutUser() throws DataAccessException {
        AuthData authData = ServiceUtils.getAuth(request.authToken());
        if (authData == null) {
            return new LogoutResult("Not authorized");
        }
        ServiceUtils.deleteAuth(request.authToken());
        return new LogoutResult("");
    }
}
