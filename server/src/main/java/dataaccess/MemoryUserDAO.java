package dataaccess;

import model.UserData;

import java.util.HashMap;

public class MemoryUserDAO implements UserDAO {

    final private HashMap<String, UserData> users = new HashMap<>();

    @Override
    public void clearUsers() throws DataAccessException {
        users.clear();
    }

    @Override
    public void createUser(UserData data) throws DataAccessException {
        users.put(data.username(), data);
    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        return users.get(username);
    }
}
