package server;

import com.google.gson.Gson;
import dataaccess.MemoryDataAccess;
import datamodel.*;
import io.javalin.*;
import io.javalin.http.Context;
import org.eclipse.jetty.server.Authentication;
import service.UserService;

import java.util.Map;

public class Server {

    private final Javalin server;
    private final UserService userService;


    public Server() {
        var dataAccess = new MemoryDataAccess();
        userService = new UserService(dataAccess);
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
        } catch (Exception e) {
            var msg = "{\"message\": \"Error: already taken\"}";
            ctx.status(403).result(msg); // stub
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
