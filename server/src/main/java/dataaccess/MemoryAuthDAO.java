package dataaccess;

import model.AuthData;

import java.util.HashMap;

public class MemoryAuthDAO implements AuthDAO {

    final private HashMap<String, AuthData> tokens = new HashMap<>();

    @Override
    public void clearAuth() throws DataAccessException {
        tokens.clear();
    }

    @Override
    public void createAuth(AuthData data) throws DataAccessException {
        tokens.put(data.authToken(), data);
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        return tokens.get(authToken);
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        tokens.remove(authToken);
    }
}
