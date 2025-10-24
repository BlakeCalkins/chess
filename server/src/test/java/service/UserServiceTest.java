package service;

import dataaccess.DataAccess;
import dataaccess.MemoryDataAccess;
import datamodel.UserData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Test
    void register() throws Exception {
        DataAccess db = new MemoryDataAccess();
        var user = new UserData("joe", "j@j.com", "pswd");
        var userService = new UserService(db);
        var authData = userService.register(user);
        assertNotNull(authData);
        assertEquals(user.username(), authData.username());
        assertFalse(authData.authToken().isEmpty());
    }

    @Test
    void registerInvalidUsername() throws Exception {
        DataAccess db = new MemoryDataAccess();
        var user = new UserData(null, "j@j.com", "pswd");
        var userService = new UserService(db);
        assertThrows(Exception.class, () -> userService.register(user));
    }

    @Test
    void registerDuplicateUsername() throws Exception {
        DataAccess db = new MemoryDataAccess();
        var user = new UserData("Blake", "blake@cool.com", "pswd");
        var user2 = new UserData("Blake", "blake@yahoo.com", "password");
        var userService = new UserService(db);
        userService.register(user);
        assertThrows(Exception.class, () -> userService.register(user2));
    }
}