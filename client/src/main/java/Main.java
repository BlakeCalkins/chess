import chess.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("♕ Welcome to the 240 Chess Client. Type help to get started.");
        label:
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
                    }
                    break;
                case "login":
                    if (commands.length != 3) {
                        String amt = (commands.length > 3) ? "many" : "few";
                        System.out.printf("Too %s inputs, type in the format: login <USERNAME> <PASSWORD> %n", amt);
                    } else {
                        System.out.println("logging in...");
                    }
                    break;
                case "quit":
                    break label;
                default:
                    System.out.println("register <USERNAME> <PASSWORD> <EMAIL> - to create an account");
                    System.out.println("login <USERNAME> <PASSWORD> - to play chess");
                    System.out.println("quit - playing chess");
                    System.out.println("help - with possible commands");
            }
        }
    }
}