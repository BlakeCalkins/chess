package dataaccess;

import model.AuthData;

public interface AuthDAO {
    void clearAuth() throws DataAccessException;

    void createAuth(AuthData data) throws DataAccessException;

    AuthData getAuth(String authToken) throws DataAccessException;

    void deleteAuth(String authToken) throws DataAccessException;
}
