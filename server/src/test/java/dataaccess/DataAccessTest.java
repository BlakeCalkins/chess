package dataaccess;

import datamodel.UserData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataAccessTest {

    // petshop has example of test for phase 3 and 4 at same time.

    @Test
    void clear() {
        DataAccess db = new MemoryUserDataAccess();
        db.createUser(new UserData("joe", "j@j.com", "pswd"));
        db.clear();
        assertNull(db.getUser("joe"));
    }

    @Test
    void createUser() {
        DataAccess db = new MemoryUserDataAccess();
        var user = new UserData("joe", "j@j.com", "pswd");
        db.createUser(user);
        assertEquals(user, db.getUser(user.username()));
    }

    @Test
    void getUser() {
    }
}