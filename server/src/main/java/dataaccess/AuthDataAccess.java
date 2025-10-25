package dataaccess;

public interface AuthDataAccess {
    void clear();
    Boolean verifyAuth(String authToken);
    void deleteAuth(String authToken);
    void add(String authToken);
}
