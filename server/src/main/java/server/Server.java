package server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dataaccess.*;
import datamodel.*;
import io.javalin.*;
import io.javalin.http.Context;
import service.AlreadyTakenException;
import service.BadRequestException;
import service.Service;
import service.UnauthorizedException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server {

    private final Javalin server;
    private final Service service;


    public Server() {
        try {
            var userDataAccess = new MySQLUserDAO();
            var authDataAccess = new MySQLAuthDAO();
            var gameDataAccess = new MySQLGameDAO();
            service = new Service(userDataAccess, authDataAccess, gameDataAccess);
            server = Javalin.create(config -> config.staticFiles.add("web"));
        } catch (DataAccessException e) {
            throw new RuntimeException();
        }
        // Register your endpoints and exception handlers here.
        server.delete("db", this::clear);
        server.post("user", this::register);
        server.post("session", this::login);
        server.delete("session", this::logout);
        server.get("game", this::listGames);
        server.post("game", this::createGame);
        server.put("game", this::joinGame);


    }

    private void clear(Context ctx) {
        try {
            service.clear();
            ctx.status(200).result("{}");
        } catch (Exception e) {
            var msg = "{\"message\": \"Error: " + e.getMessage() + "\"}";
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
            var msg = "{\"message\": \"Error: " + e.getMessage() + "\"}";
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
            var msg = "{\"message\": \"Error: " + e.getMessage() + "\"}";
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
            var msg = "{\"message\": \"Error: " + e.getMessage() + "\"}";
            ctx.status(500).result(msg);
        }
    }

    private void listGames(Context ctx) {
        try {
            var serializer = new Gson();
            var auth = ctx.header("authorization");

            List<GameData> games = service.listGames(auth);
            Map<String, Object> response = new HashMap<>();
            response.put("games", games);
            ctx.status(200).result(serializer.toJson(response));
        }  catch (UnauthorizedException e) {
            ctx.status(e.getCode()).result(e.getMessage());
        } catch (Exception e) {
            var msg = "{\"message\": \"Error: " + e.getMessage() + "\"}";
            ctx.status(500).result(msg);
        }

    }

    private void createGame(Context ctx) {
        try {
            var serializer = new Gson();
            var auth = ctx.header("authorization");
            String requestJson = ctx.body();
            var gameData = serializer.fromJson(requestJson, GameData.class);

            int gameID = service.createGame(gameData, auth);
            Map<String, Object> response = new HashMap<>();
            response.put("gameID", gameID);
            ctx.status(200).result(serializer.toJson(response));


        } catch (BadRequestException e) {
            ctx.status(e.getCode()).result(e.getMessage());
        } catch (UnauthorizedException e) {
            ctx.status(e.getCode()).result(e.getMessage());
        } catch (Exception e) {
            var msg = "{\"message\": \"Error: " + e.getMessage() + "\"}";
            ctx.status(500).result(msg);
        }
    }

    private void joinGame(Context ctx) {
        try {
            var serializer = new Gson();
            var auth = ctx.header("authorization");
            String requestJson = ctx.body();
            Map<String, Object> data = serializer.fromJson(requestJson, new TypeToken<Map<String, Object>>(){}.getType());
            Double raw = (Double) data.get("gameID");
            Integer gameID;
            if (raw == null) {
                gameID = null;
            } else {
                gameID = raw.intValue();
            }

            service.joinGame((String) data.get("playerColor"), gameID, auth);
            ctx.status(200).result("{}");
        } catch (UnauthorizedException e) {
            ctx.status(e.getCode()).result(e.getMessage());
        } catch (BadRequestException e) {
            ctx.status(e.getCode()).result(e.getMessage());
        } catch (AlreadyTakenException e) {
            ctx.status(e.getCode()).result(e.getMessage());
        } catch (Exception e) {
            var msg = "{\"message\": \"Error: " + e.getMessage() + "\"}";
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
