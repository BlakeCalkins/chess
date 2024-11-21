package service;

import dataaccess.DataAccessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LoginTest {
    private static LoginService loginService;


    @BeforeAll
    public static void setUp() throws DataAccessException {
        loginService = new LoginService(new LoginRequest("Blake", "assword"));
        RegisterService registerService = new RegisterService(new RegisterRequest("Blake", "assword", "blake@calkins.com"));
        registerService.registerUser();
    }

    @Test
    public void testLoginSuccessful() throws DataAccessException {
        LoginResult result = loginService.loginUser();
        Assertions.assertEquals(result.username(), "Blake");
        Assertions.assertEquals(result.message(), "");
    }

    @Test
    public void testLoginFail() throws DataAccessException {
        LoginService badLogin = new LoginService(new LoginRequest("Blake", "Password"));
        LoginResult badResult = badLogin.loginUser();
        Assertions.assertEquals("Access denied, wrong password.", badResult.message());
    }
}
