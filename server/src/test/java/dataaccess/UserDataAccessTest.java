package dataaccess;

import datamodel.UserData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDataAccessTest {

    UserData user = new UserData("blake", "blake@yahoo.com", "password");

    @AfterEach
    void clearOut() throws DataAccessException {
        UserDataAccess db = new MySQLUserDAO();
        db.clear();
    }

    @Test
    void clear() throws DataAccessException {
        UserDataAccess db = new MySQLUserDAO();
        db.createUser(user);
        db.clear();
        assertNull(db.getUser("joe"));
    }

    @Test
    void createUserAndGetUser() throws DataAccessException {
        UserDataAccess db = new MySQLUserDAO();
        db.createUser(user);
        assertEquals(user.username(), db.getUser(user.username()).username());
        assertEquals(user.email(), db.getUser(user.username()).email());
        assertNotEquals(user.password(), db.getUser(user.username()).password());
    }

    @Test
    void createUserTaken() throws DataAccessException {
        UserDataAccess db = new MySQLUserDAO();
        db.createUser(user);
        assertThrows(RuntimeException.class,() -> db.createUser(user));
    }

    @Test
    void getUserBadUser() throws DataAccessException {
        UserDataAccess db = new MySQLUserDAO();
        db.createUser(user);
        assertNull(db.getUser(new UserData("jorge", "jorge@hotmail.com", "pass").username()));
    }

    @Test
    void validPassword() throws DataAccessException {
        UserDataAccess db = new MySQLUserDAO();
        db.createUser(user);
        assertTrue(db.validPassword(user));
    }

    @Test
    void invalidPassword() throws DataAccessException {
        UserDataAccess db = new MySQLUserDAO();
        db.createUser(user);
        assertFalse(db.validPassword(new UserData("blake", "blake@yahoo.com", "bad_password")));
    }
}