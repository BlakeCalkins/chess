package dataaccess;

import datamodel.AuthData;

import java.util.HashMap;

public interface AuthDataAccess {
    HashMap<String, String> getAuthTokens();
    void clear() throws DataAccessException;
    Boolean verifyAuth(String authToken) throws DataAccessException;
    AuthData getAuth(String authToken);
    void deleteAuth(String authToken);
    void add(String authToken, String username);
}
