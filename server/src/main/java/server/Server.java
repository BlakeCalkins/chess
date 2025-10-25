package server;

import com.google.gson.Gson;
import dataaccess.MemoryAuthDataAccess;
import dataaccess.MemoryUserDataAccess;
import datamodel.*;
import io.javalin.*;
import io.javalin.http.Context;
import service.AlreadyTakenException;
import service.BadRequestException;
import service.Service;
import service.UnauthorizedException;

public class Server {

    private final Javalin server;
    private final Service service;


    public Server() {
        var userDataAccess = new MemoryUserDataAccess();
        var authDataAccess = new MemoryAuthDataAccess();
        service = new Service(userDataAccess, authDataAccess);
        server = Javalin.create(config -> config.staticFiles.add("web"));

        // Register your endpoints and exception handlers here.
        server.delete("db", this::clear); // stub, call to service to call clear
        server.post("user", this::register);
        server.post("session", this::login);
        server.delete("session", this::logout);


    }

    private void clear(Context ctx) {
        try {
            service.clear();
            ctx.status(200).result("{}");
        } catch (Exception e) {
            var msg = "{\"message\": \"Error: 500\"}";
            ctx.status(500).result(msg);
        }
    }

    private void register(Context ctx) {
        try {
            var serializer = new Gson();
            String requestJson = ctx.body();
            var user = serializer.fromJson(requestJson, UserData.class);

            // call to the service and register

            var authData = service.register(user);

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

    private void login(Context ctx) {
        try {
            var serializer = new Gson();
            String requestJson = ctx.body();
            var user = serializer.fromJson(requestJson, UserData.class);

            var authData = service.login(user);
            ctx.result(serializer.toJson(authData));

        } catch (BadRequestException e) {
            ctx.status(e.getCode()).result(e.getMessage());
        } catch (UnauthorizedException e) {
            ctx.status(e.getCode()).result(e.getMessage());
        } catch (Exception e) {
            var msg = "{\"message\": \"Error: 500\"}";
            ctx.status(500).result(msg);
        }
    }

    private void logout(Context ctx) {
        try {
            var serializer = new Gson();
            String requestJson = ctx.header("authorization");
            var auth = serializer.fromJson(requestJson, String.class);

            service.logout(auth);
            ctx.status(200).result("{}");
        } catch (UnauthorizedException e) {
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
