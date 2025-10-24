package dataaccess;

import datamodel.*;

public interface UserDataAccess {
    void clear();
    void createUser(UserData user);
    UserData getUser(String username);
    Boolean validPassword(UserData user);
}
