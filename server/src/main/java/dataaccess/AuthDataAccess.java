package dataaccess;

public interface AuthDataAccess {
    Boolean verifyAuth(String authToken);
    void deleteAuth(String authToken);
    void add(String authToken);
}
