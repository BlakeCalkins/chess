package server;

import com.google.gson.Gson;
import dataaccess.MemoryUserDataAccess;
import datamodel.*;
import io.javalin.*;
import io.javalin.http.Context;
import service.AlreadyTakenException;
import service.BadRequestException;
import service.Service;

public class Server {

    private final Javalin server;
    private final Service userService;


    public Server() {
        var dataAccess = new MemoryUserDataAccess();
        userService = new Service(dataAccess);
        server = Javalin.create(config -> config.staticFiles.add("web"));

        // Register your endpoints and exception handlers here.
        server.delete("db", ctx -> ctx.result("{}")); // stub, call to service to call clear
        server.post("user", this::register);


    }

    private void register(Context ctx) {
        try {
            var serializer = new Gson();
            String requestJson = ctx.body();
            var user = serializer.fromJson(requestJson, UserData.class);

            // call to the service and register

            var authData = userService.register(user);

            ctx.result(serializer.toJson(authData));
        } catch (BadRequestException e) {
            ctx.status(e.getCode()).result(e.getMessage());
        } catch (AlreadyTakenException e) {
            ctx.status(e.getCode()).result(e.getMessage());
        } catch (Exception e) {
            var msg = "{\"message\": \"Error: 500\"}";
            ctx.status(500).result(msg);
        }
    }

    public int run(int desiredPort) {
        server.start(desiredPort);
        return server.port();
    }

    public void stop() {
        server.stop();
    }
}
