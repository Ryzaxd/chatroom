package com.example.chatroom.Client;

import javafx.application.Application;

public class Client {
    public static void main(String[] args) {
        int antalKlienter;
        antalKlienter = 3;

        for (int i = 0; i < antalKlienter; i++) {

            Thread klientThread = new Thread(() -> {
                Application.launch(ClientGUI.class, args);
            });

            klientThread.start();
        }
    }
}

