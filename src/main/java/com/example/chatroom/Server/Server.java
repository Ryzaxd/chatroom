package com.example.chatroom.Server;

import com.example.chatroom.Client.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final int PORT = 8080;
    private List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        new Server().startServer();
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Chat Server is running...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

                // Create a new client handler for the connected client
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);

                // Check for username uniqueness
                if (!isUsernameUnique(clientHandler.getUsername())) {
                    System.out.println("Username is not unique. Client disconnected.");
                    clientHandler.sendMessage("ERROR: Username is not unique. Please choose a different username.");
                    clientSocket.close();
                    continue;
                }

                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Check if a username is unique
    public boolean isUsernameUnique(String username) {
        for (ClientHandler client : clients) {
            if (client.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }

    public void broadcastMessage(String message) {
        System.out.println("Broadcasting: " + message);
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    public void removeClient(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }
}

