package service;

import dataaccess.DataAccessException;
import model.AuthData;
import model.UserData;

public class RegisterService {
    RegisterRequest request;
    UserData userData;
    AuthData authData;

    public RegisterService(RegisterRequest request) {
        this.request = request;
        userData = new UserData(request.username(), request.password(), request.email());
        authData = new AuthData(ServiceUtils.generateToken(), request.username());
    }

    public RegisterResult registerUser() throws DataAccessException, ServiceException {
        if (ServiceUtils.getUser(request.username()) != null) {
            throw new ServiceException("Error: already taken", ServiceException.Type.INPUTTAKEN);
        }

        ServiceUtils.createUser(userData);
        ServiceUtils.createAuth(authData);
        return new RegisterResult(request.username(), authData.authToken(), "");
    }
}
