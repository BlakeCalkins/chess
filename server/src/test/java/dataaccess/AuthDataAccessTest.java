package dataaccess;

import datamodel.AuthData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class AuthDataAccessTest {

    String username = "blake";
    String authToken = UUID.randomUUID().toString();
    AuthDataAccess db = new MySQLAuthDAO();

    public AuthDataAccessTest() throws DataAccessException {
    }

    @AfterEach
    void clearOut() throws DataAccessException {
        db.clear();
    }

    @Test
    void clear() throws DataAccessException {
        db.add(authToken, username);
        db.clear();
        assertNull(db.getAuth(authToken));
    }

    @Test
    void addAndGetAuth() {
        db.add(authToken, username);
        assertEquals(new AuthData(username, authToken), db.getAuth(authToken));
    }

    @Test
    void badd() {
        db.add(authToken, username);
        assertThrows(RuntimeException.class, () -> db.add(authToken, username));
    }

    @Test
    void getAuthGoneWrong() {
        db.add(authToken, username);
        assertNull(db.getAuth("not_a_good_auth_token"));
    }

    @Test
    void verifyAuth() throws DataAccessException {
        db.add(authToken, username);
        assertTrue(db.verifyAuth(authToken));
    }

    @Test
    void verifyBadAuth() throws DataAccessException {
        db.add(authToken, username);
        assertFalse(db.verifyAuth("not_a_good_auth_token"));
    }

    @Test
    void deleteAuth() {
        db.add(authToken, username);
        db.deleteAuth(authToken);
        assertNull(db.getAuth(authToken));
    }

    @Test
    void deleteNoAuth() {
        db.add(authToken, username);
        db.deleteAuth("lol");
        assertEquals(authToken, db.getAuth(authToken).authToken());
    }
}
