package dataaccess;


import java.util.HashSet;
import java.util.Set;

public class MemoryAuthDataAccess implements AuthDataAccess {
    private final Set<String> authTokens = new HashSet<>();

    @Override
    public Boolean verifyAuth(String authToken) {
        return authTokens.contains(authToken);
    }

    @Override
    public void deleteAuth(String authToken) {
        authTokens.remove(authToken);
    }

    @Override
    public void add(String authToken) {
        authTokens.add(authToken);
    }
}
