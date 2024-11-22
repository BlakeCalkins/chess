package server;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import service.*;
import spark.*;

public class Server {
    RegisterService regSer;
    ClearService clearSer;
    LoginService logSer;
    LogoutService outSer;

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", this::clear);
        Spark.post("/user", this::registerUser);
        Spark.post("/session", this::loginUser);
        Spark.delete("/session", this::logoutUser);


        Spark.exception(ServiceException.class, this::handleException);

        //This line initializes the server and can be removed once you have a functioning endpoint 
        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    private void handleException(ServiceException exception, Request req, Response res) {
        String result = new Gson().toJson(new ExceptionResult(exception.getMessage()));
        res.body(result);
        res.status(switch (exception.getType()){
            case BADINPUT -> 400;
            case BADAUTH -> 401;
            case INPUTTAKEN -> 403;
        });
    }

    private Object registerUser(Request req, Response res) throws DataAccessException, ServiceException {
        var reg = new Gson().fromJson(req.body(), RegisterRequest.class);
        regSer = new RegisterService(reg);
        RegisterResult result = regSer.registerUser();

        return new Gson().toJson(result);
    }
    
    private Object clear(Request req, Response res) throws DataAccessException {
        clearSer = new ClearService();
        clearSer.clear();
        res.status(200);
        return "";
    }

    private Object loginUser(Request req, Response res) throws DataAccessException, ServiceException {
        var log = new Gson().fromJson(req.body(), LoginRequest.class);
        logSer = new LoginService(log);
        LoginResult result = logSer.loginUser();
        return new Gson().toJson(result);
    }

    private Object logoutUser(Request req, Response res) throws DataAccessException, ServiceException {
        var out = new Gson().fromJson(req.body(), LogoutRequest.class);
        outSer = new LogoutService(out);
        LogoutResult result = outSer.logoutUser();
        return new Gson().toJson(result);
    }


    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
