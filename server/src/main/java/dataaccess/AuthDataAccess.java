package dataaccess;

import datamodel.AuthData;

import java.util.HashMap;

public interface AuthDataAccess {
    HashMap<String, String> getAuthTokens();
    void clear();
    Boolean verifyAuth(String authToken);
    AuthData getAuth(String authToken);
    void deleteAuth(String authToken);
    void add(String username, String authToken);
}
