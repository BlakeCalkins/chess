package service;

import dataaccess.DataAccessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LoginTest {
    private static LoginService loginService;


    @BeforeAll
    public static void setUp() throws DataAccessException, ServiceException {
        loginService = new LoginService(new LoginRequest("Blake", "assword"));
        RegisterService registerService = new RegisterService(new RegisterRequest("Blake", "assword", "blake@calkins.com"));
        registerService.registerUser();
    }

    @Test
    public void testLoginSuccessful() throws DataAccessException, ServiceException {
        LoginResult result = loginService.loginUser();
        Assertions.assertEquals(result.username(), "Blake");
        Assertions.assertEquals(result.message(), "");
    }

    @Test
    public void testLoginFail() throws DataAccessException, ServiceException {
        LoginService badLogin = new LoginService(new LoginRequest("Blake", "Password"));
        Exception exception = Assertions.assertThrows(ServiceException.class, badLogin::loginUser, "Access denied, wrong password.");
        Assertions.assertEquals("Access denied, wrong password.", exception.getMessage());
    }
}
