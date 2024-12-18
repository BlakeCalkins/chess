package service;

import dataaccess.DataAccessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LogoutTest {
    private static LogoutService logoutService;
    private static RegisterService registerService;

    @BeforeAll
    public static void setUp() {
        registerService = new RegisterService(new RegisterRequest("Blake", "password", "blake@fake.com"));
    }

    @Test
    public void testLogoutSuccessful() throws DataAccessException, ServiceException {
        String authToken = registerService.registerUser().authToken();
        logoutService = new LogoutService(new LogoutRequest(authToken));
        LogoutResult result = logoutService.logoutUser();
        Assertions.assertEquals("", result.errMessage());
    }

    @Test
    public void testLogoutFail() throws DataAccessException, ServiceException {
        logoutService = new LogoutService(new LogoutRequest(ServiceUtils.generateToken()));
        Exception exception = Assertions.assertThrows(ServiceException.class, logoutService::logoutUser, "Error: unauthorized");
        Assertions.assertEquals("Error: unauthorized", exception.getMessage());
    }
}
