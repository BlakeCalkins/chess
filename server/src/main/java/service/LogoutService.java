package service;

import dataaccess.DataAccessException;

public class LogoutService {
    LogoutRequest request;

    public LogoutService(LogoutRequest request) {
        this.request = request;
    }

    public LogoutResult logoutUser() throws DataAccessException, ServiceException {
        if (request == null || !ServiceUtils.verifyAuth(request.authToken())) {
            throw new ServiceException("Error: unauthorized", ServiceException.Type.BADAUTH);
        }
        ServiceUtils.deleteAuth(request.authToken());
        return new LogoutResult("");
    }
}
