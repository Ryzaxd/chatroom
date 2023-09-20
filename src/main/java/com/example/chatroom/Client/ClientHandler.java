package com.example.chatroom.Client;

import com.example.chatroom.Server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private Server server;
    private PrintWriter out;
    private String username;
    private boolean userHasJoined = false;

    public ClientHandler(Socket socket, Server server) {
        this.clientSocket = socket;
        this.server = server;
    }

    public String getUsername() {
        return username;
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public class MessageTypes {
        public static final String JOIN = "/join";
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            this.out = out;
            handleClientCommunication(in);
        } catch (IOException e) {

            e.printStackTrace();
        } finally {
            handleClientDisconnection();
        }
    }

    private void handleClientCommunication(BufferedReader in) throws IOException {
        String message;
        while ((message = in.readLine()) != null) {
            if (message.startsWith(MessageTypes.JOIN)) {
                handleJoinMessage(message);

                userHasJoined = true;
            } else {
                handleChatMessage(message);
            }
        }
    }

    public class MessageParser {
        public static String extractUsername(String message) {

            String[] parts = message.split(" ");
            if (parts.length >= 2) {
                return parts[1];
            } else {
                return "";
            }
        }
    }

    private void handleJoinMessage(String joinMessage) {

        String requestedUsername = MessageParser.extractUsername(joinMessage);
        if (isValidUsername(requestedUsername)) {
            username = requestedUsername;
            server.broadcastMessage(username + " has joined the chat.");
        } else {
            sendMessage("ERROR: Invalid username. Please choose a different one.");

        }
    }

    private boolean isValidUsername(String username) {
        return !username.isEmpty() && !server.isUsernameTaken(username);
    }

    private void handleChatMessage(String chatMessage) {
        if (userHasJoined) {
            server.broadcastMessage(getTimestampedMessage(chatMessage));
        } else {
            sendMessage("ERROR: You must join the chat before sending messages.");
        }
    }

    private boolean isValidChatMessage(String chatMessage) {
        return !chatMessage.isEmpty();
    }

    private void handleClientDisconnection() {
        server.removeClient(this);
        if (username != null) {
            server.broadcastMessage(username + " has left the chat.");
        }
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getTimestampedMessage(String message) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String timestamp = dateFormat.format(new Date());
        return "[" + timestamp + "] " + username + ": " + message;
    }
}
