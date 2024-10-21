package dataaccess;

import model.AuthData;

public interface AuthDAO {
    void clearAuth() throws DataAccessException;

    void createAuth(AuthData data) throws DataAccessException;

    AuthData getAuth(AuthData data) throws DataAccessException;

    void deleteAuth() throws DataAccessException;
}
