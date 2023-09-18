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

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            this.out = out;
            String message;
            while ((message = in.readLine()) != null) {
                if (message.startsWith("/join")) {

                    server.broadcastMessage(message);
                } else {

                    server.broadcastMessage(getTimestampedMessage(message));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            server.removeClient(this);
            server.broadcastMessage(username + " has left the chat.");
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getTimestampedMessage(String message) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String timestamp = dateFormat.format(new Date());
        return "[" + timestamp + "] " + username + ": " + message;
    }
}
