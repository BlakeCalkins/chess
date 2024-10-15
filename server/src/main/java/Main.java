import chess.*;
import server.Server;

public class Main {
    public static void main(String[] args) {
        var server = new Server();
        var runner = server.run(8080);
        System.out.println("♕ 240 Chess Server: " + runner);
    }
}