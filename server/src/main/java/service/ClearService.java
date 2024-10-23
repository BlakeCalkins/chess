package service;

import dataaccess.DataAccessException;

public class ClearService {

    public void clear() throws DataAccessException {
        ServiceUtils.clearUsers();
        ServiceUtils.clearAuth();
        ServiceUtils.clearGames();
    }
}
