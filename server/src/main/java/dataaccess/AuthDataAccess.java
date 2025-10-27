package dataaccess;

import datamodel.AuthData;

public interface AuthDataAccess {
    void clear();
    Boolean verifyAuth(String authToken);
    AuthData getAuth(String authToken);
    void deleteAuth(String authToken);
    void add(String username, String authToken);
}
