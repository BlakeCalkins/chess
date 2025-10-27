package dataaccess;

import datamodel.*;

import java.util.HashMap;

public interface UserDataAccess {
    HashMap<String, UserData> getUsers();
    void clear();
    void createUser(UserData user);
    UserData getUser(String username);
    Boolean validPassword(UserData user);
}
