package dataaccess;


import datamodel.AuthData;

import java.util.HashMap;

public class MemoryAuthDataAccess implements AuthDataAccess {
    private final HashMap<String, String> authTokens = new HashMap<>();

    @Override
    public HashMap<String, String> getAuthTokens() {
        return authTokens;
    }

    @Override
    public void clear() {
        authTokens.clear();
    }

    @Override
    public Boolean verifyAuth(String authToken) {
        return authTokens.containsKey(authToken);
    }

    @Override
    public AuthData getAuth(String authToken) {
        return new AuthData(authTokens.get(authToken), authToken);
    }

    @Override
    public void deleteAuth(String authToken) {
        authTokens.remove(authToken);
    }

    @Override
    public void add(String authToken, String username) {
        authTokens.put(authToken, username);
    }
}
