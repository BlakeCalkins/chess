package server;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import service.ClearService;
import service.RegisterRequest;
import service.RegisterService;
import spark.*;

public class Server {
    RegisterService regSer;
    ClearService clearSer;

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", this::clear);
        Spark.post("/user", this::registerUser);


        //This line initializes the server and can be removed once you have a functioning endpoint 
        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }


    private Object registerUser(Request req, Response res) throws DataAccessException {
        var reg = new Gson().fromJson(req.body(), RegisterRequest.class);
        regSer = new RegisterService(reg);
        regSer.registerUser();
        return new Gson().toJson(regSer);
    }
    
    private Object clear(Request req, Response res) throws DataAccessException {
        clearSer = new ClearService();
        clearSer.clear();
        res.status(200);
        return "";

    }


    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
