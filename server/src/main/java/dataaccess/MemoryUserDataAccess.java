package dataaccess;

import datamodel.UserData;

import java.util.HashMap;
import java.util.Objects;

public class MemoryUserDataAccess implements UserDataAccess {
    private final HashMap<String, UserData> users = new HashMap<>();

    @Override
    public HashMap<String, UserData> getUsers() {
        return users;
    }

    @Override
    public void clear() {
        users.clear();
    }

    @Override
    public void createUser(UserData user) {
        users.put(user.username(), user);
    }

    @Override
    public UserData getUser(String username) {
        return users.get(username);
    }

    @Override
    public Boolean validPassword(UserData user) {
        UserData data = users.get(user.username());
        return Objects.equals(data.password(), user.password());
    }
}
