package com.example.chatroom.LoginSystem;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LoginSystem {
    private static Map<String, String> userInfo = new HashMap<>();

    static {
        
        userInfo.put("john", "john123");
        userInfo.put("bob", "bob123");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Login System");
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            if (authenticateUser(username, password)) {
                System.out.println("Login successful!");
                break;
            } else {
                System.out.println("Login failed. Try again.");
            }
        }
    }

    private static boolean authenticateUser(String username, String password) {
        String storedPassword = userInfo.get(username);
        return storedPassword != null && storedPassword.equals(password);
    }
}
