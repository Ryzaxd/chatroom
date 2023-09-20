package com.example.chatroom.Client;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ClientGUI extends Application {
    private final TextArea chatArea = new TextArea();
    private final TextField messageInput = new TextField();
    private final TextField usernameInput = new TextField();
    private PrintWriter out;
    private Stage primaryStage;
    private boolean usernameEntered = false;
    private Socket socket;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("ArtoChat");

        GridPane usernamePane = new GridPane();
        usernamePane.setHgap(10);
        usernamePane.setVgap(10);
        usernamePane.setPadding(new Insets(10));

        Label usernameLabel = new Label("Enter your username:");
        usernamePane.add(usernameLabel, 0, 0);
        usernamePane.add(usernameInput, 1, 0);
        Button setUsernameButton = new Button("Enter");
        usernamePane.add(setUsernameButton, 2, 0);

        setUsernameButton.setOnAction(e -> setUsername());

        Scene usernameScene = new Scene(usernamePane, 350, 100);
        usernamePane.setStyle("-fx-background-color: #27b920; -fx-padding: 10;");

        primaryStage.getIcons().add(new Image("C:\\Users\\Bruger\\OneDrive\\Dokumenter\\GitHub\\chatroom\\src\\main\\resources\\com\\example\\chatroom\\Pictures\\artochat.png"));
        primaryStage.setScene(usernameScene);
        primaryStage.show();
    }

    private void setUsername() {
        String username = usernameInput.getText();
        if (!username.isEmpty()) {
            primaryStage.setTitle("ArtoChat - " + username);
            usernameEntered = true;

            chatArea.setEditable(false);
            chatArea.setWrapText(true);
            chatArea.setPrefHeight(500);
            chatArea.setMaxHeight(Double.MAX_VALUE);

            messageInput.setPrefHeight(30);

            VBox chatContainer = new VBox(10, chatArea, messageInput);
            chatContainer.setPadding(new Insets(10));

            chatContainer.setStyle("-fx-background-color: #27b920; -fx-padding: 10;");

            Scene chatScene = new Scene(chatContainer, 920, 620);
            primaryStage.setScene(chatScene);

            messageInput.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    sendMessageWithTimestamp();
                }
            });

            try {
                socket = new Socket("localhost", 8080);
                out = new PrintWriter(socket.getOutputStream(), true);

                out.println("/join " + username);

                new Thread(() -> {
                    try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                        String message;
                        while ((message = in.readLine()) != null) {
                            chatArea.appendText(message + "\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMessageWithTimestamp() {
        if (usernameEntered) {
            String message = messageInput.getText();
            if (!message.isEmpty()) {
                out.println(message);
                messageInput.clear();
            }
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }

    public void launchClient(String[] args) {
        launch(args);
    }

    public static void main(String[] args) {
        launch(args);
    }
}