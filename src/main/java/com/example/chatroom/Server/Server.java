package com.example.chatroom.Server;

import com.example.chatroom.Client.ClientHandler;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    private static final int PORT = 8080;
    private final List<ClientHandler> clients = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        new Server().startServer();
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Chat Server is running...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());


                ClientHandler clientHandler = new ClientHandler(clientSocket, this);


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

    public boolean isUsernameUnique(String username) {
        for (ClientHandler client : clients) {
            if (client.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }

    public boolean isUsernameTaken(String username) {
        for (ClientHandler client : clients) {
            if (client.getUsername() != null && client.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public void removeClient(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }

    public void broadcastMessage(String message) {
        System.out.println("Broadcasting: " + message);
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }
}

