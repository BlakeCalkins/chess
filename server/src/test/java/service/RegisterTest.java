package service;

import dataaccess.DataAccessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RegisterTest {
    private static RegisterService service;

    @BeforeAll
    public static void setUp() {
        service = new RegisterService(new RegisterRequest("John", "pass", "john@yahoo.com"));
    }

    @Test
    public void testAuthTokens() throws DataAccessException, ServiceException {
        RegisterResult testResult = new RegisterResult("John", ServiceUtils.generateToken(), "");
        RegisterResult actualResult = service.registerUser();
        System.out.println("test result auth token:" + testResult.authToken());
        System.out.print("actual result auth token:" + actualResult.authToken());
        Assertions.assertNotEquals(testResult, actualResult);
        Assertions.assertEquals("John", testResult.username());
    }

    @Test
    public void testUserAlreadyExists() throws DataAccessException, ServiceException {
        service.registerUser();
        RegisterService testService = new RegisterService(new RegisterRequest("John", "pswd", "john@failure.com"));
        String errMessage = testService.registerUser().message();
        Assertions.assertEquals("Error: User already exists.", errMessage);
    }
}
