package service;

import dataaccess.DataAccessException;

public class LogoutService {
    LogoutRequest request;

    public LogoutService(LogoutRequest request) {
        this.request = request;
    }

    public LogoutResult logoutUser() throws DataAccessException {
        if (!ServiceUtils.verifyAuth(request.authToken())) {
            return new LogoutResult("Not authorized");
        }
        ServiceUtils.deleteAuth(request.authToken());
        return new LogoutResult("");
    }
}
