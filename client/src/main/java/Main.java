import ui.EndpointParser;

import java.util.Scanner;

public class Main {
    static EndpointParser ep = new EndpointParser();
    static String username;

    private static boolean preLoginLoop() {
        while (true) {
            System.out.print("[LOGGED_OUT] >>> ");
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            var commands = line.split(" ");
            String command = commands[0];
            switch (command) {
                case "register":
                    if (commands.length != 4) {
                        String amt = (commands.length > 4) ? "many" : "few";
                        System.out.printf("Too %s inputs, type in the format: register <USERNAME> <PASSWORD> <EMAIL>%n", amt);
                    } else {
                        System.out.println("registering...");
                         ep.register(commands[1], commands[2], commands[3]);
                         username = commands[1];
                         return true;
                    }
                    break;
                case "login":
                    if (commands.length != 3) {
                        String amt = (commands.length > 3) ? "many" : "few";
                        System.out.printf("Too %s inputs, type in the format: login <USERNAME> <PASSWORD> %n", amt);
                    } else {
                        System.out.println("logging in...");
                        if (ep.login(commands[1], commands[2])) {
                            username = commands[1];
                            return true;
                        }
                        continue;
                    }
                    break;
                case "quit":
                    System.out.println("Bye bye!");
                    return false;
                default:
                    System.out.println("register <USERNAME> <PASSWORD> <EMAIL> - to create an account");
                    System.out.println("login <USERNAME> <PASSWORD> - to play chess");
                    System.out.println("quit - playing chess");
                    System.out.println("help - with possible commands");
            }
        }
    }

    private static boolean postLoginLoop() {
        while (true) {
            System.out.print("[LOGGED_IN] >>> ");
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            var commands = line.split(" ");
            String command = commands[0];
            switch (command) {
                case "create":
                    if (commands.length != 2) {
                        String amt = (commands.length > 2) ? "many" : "few";
                        System.out.printf("Too %s inputs, type in the format: create <NAME> %n", amt);
                    } else {
                        System.out.printf("creating game %s...", commands[1]);
                        System.out.println();
                         ep.createGame(commands[1], username);
                    }
                    break;
                case "list":
                    System.out.println("Current games: ");
                     ep.listGames(username);
                    continue;
                case "join":
                    if (commands.length != 3) {
                        String amt = (commands.length > 3) ? "many" : "few";
                        System.out.printf("Too %s inputs, type in the format: join <ID> [WHITE/BLACK] %n", amt);
                    } else if (isNotInteger(commands[1])) {
                        System.out.println("Please provide an integer for the game ID.");
                    } else {
                        System.out.printf("joining game %d...", Integer.parseInt(commands[1]));
                        System.out.println();
                         ep.joinGame(commands[2], Integer.parseInt(commands[1]), username);
                    }
                    break;
                case "observe":
                    if (commands.length != 2) {
                        String amt = (commands.length > 3) ? "many" : "few";
                        System.out.printf("Too %s inputs, type in the format: observe <ID> %n", amt);
                    } else if (isNotInteger(commands[1])) {
                        System.out.println("Please provide an integer for the game ID.");
                    } else {
                        System.out.printf("observing game %d...", Integer.parseInt(commands[1]));
                        System.out.println();
                        // ep.printStoredGame(id)
                    }
                    break;
                case "logout":
                    System.out.println("logging you out...");
                    return true;
                    // ep.logout(auth)
                case "quit":
                    System.out.println("Bye bye!");
                    // ep.logout(auth)
                    return false;
                default:
                    System.out.println("create <NAME> - a game");
                    System.out.println("list - current games");
                    System.out.println("join <ID> [WHITE/BLACK] - a game");
                    System.out.println("observe <ID> - a game");
                    System.out.println("logout - when you are done");
                    System.out.println("quit - playing chess");
                    System.out.println("help - with possible commands");
            }
        }
    }

    public static boolean isNotInteger(String str) {
        try {
            Integer.parseInt(str);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    public static void main(String[] args) {
        System.out.println("♕ Welcome to the 240 Chess Client. Type help to get started.");
        while (true) {
            if (preLoginLoop()) {
                if (!postLoginLoop()) {
                    break;
                }
            } else {
                break;
            }
        }
        ep.stopServer();
    }
}