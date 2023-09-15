package com.example.chatroom.Client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Application {
    private TextArea chatArea = new TextArea();
    private TextField messageInput = new TextField();
    private PrintWriter out;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Chatroom");

        // Initialize the GUI components.
        Button sendButton = new Button("Send");
        sendButton.setOnAction(e -> sendMessage());

        // Set up the layout.
        VBox vbox = new VBox();
        vbox.getChildren().addAll(chatArea, messageInput, sendButton);
        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setScene(scene);

        // Establish a connection to the server (you'll need the server's IP address and port).
        Socket socket = new Socket("localhost", 1234);
        out = new PrintWriter(socket.getOutputStream(), true);

        // Start a thread to listen for incoming messages from the server.
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

        primaryStage.show();
    }

    private void sendMessage() {
        String message = messageInput.getText();
        if (!message.isEmpty()) {
            out.println(message);
            messageInput.clear();
        }
    }
}
