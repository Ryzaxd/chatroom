package com.example.chatroom.Client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class ClientGUI extends Application {
    private final TextArea chatArea = new TextArea();
    private final TextField messageInput = new TextField();
    private final TextField usernameInput = new TextField();
    private PrintWriter out;
    private Stage primaryStage;
    private boolean usernameEntered = false;
    private Socket socket;
    private ListView<String> userList = new ListView<>();
    private Scene usernameScene;
    private Scene chatScene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("ArtoChat");

        GridPane usernamePane = new GridPane();
        usernamePane.setHgap(10);
        usernamePane.setVgap(10);
        usernamePane.setPadding(new Insets(10));
        usernamePane.setStyle("-fx-border-color: black");


        Label usernameLabel = new Label("Enter your username:");
        usernamePane.add(usernameLabel, 0, 0);
        usernamePane.add(usernameInput, 1, 0);
        Button setUsernameButton = new Button("Enter");
        usernamePane.add(setUsernameButton, 2, 0);
        

        setUsernameButton.setOnAction(e -> chatRoom());

        StackPane Layout = new StackPane();
        Layout.getChildren().add(new Label("ArtoChat"));
        Scene usernameScene = new Scene(usernamePane, 350, 100);
        usernamePane.setStyle("-fx-background-color: #27b920; -fx-padding: 10;");



        primaryStage.getIcons().add(new Image("file:artochat.png"));
        primaryStage.setScene(usernameScene);
        primaryStage.show();


    }

    private void chatRoom() {
        String username = usernameInput.getText();
        if (!username.isEmpty()) {
            primaryStage.setTitle("ArtoChat - " + username);
            usernameEntered = true;

            chatArea.setEditable(false);
            chatArea.setWrapText(true);
            chatArea.setPrefHeight(500);
            chatArea.setMaxHeight(Double.MAX_VALUE);

            messageInput.setPrefHeight(30);

            HBox chatAndUserList = new HBox(10);
            chatAndUserList.getChildren().addAll(chatArea, userList);

            VBox chatContainer = new VBox(10, chatAndUserList, messageInput);
            chatContainer.setPadding(new Insets(10));

            chatContainer.setStyle("-fx-background-color: #27b920; -fx-padding: 10;");

            userList.setStyle("-fx-background-color: white;");


            Scene chatScene = new Scene(chatContainer, 550, 400);
            primaryStage.setScene(chatScene);

            messageInput.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    sendMessageWithTimestamp();
                }
            });

            chatScene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                if (event.getCode() == KeyCode.ESCAPE) {
                    chatScene.setRoot(usernameScene.getRoot());
                }
            });

            chatScene.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ESCAPE) {
                    primaryStage.setScene(usernameScene);
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
                            if (message.startsWith("USERLIST ")) {

                                String userListStr = message.substring(9);
                                List<String> users = new ArrayList<>();
                                String[] userArray = userListStr.split(",");
                                for (String user : userArray) {
                                    users.add(user);
                                }
                                updateUserList(users);
                            } else {

                                chatArea.appendText(message + "\n");
                            }
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

    private void updateUserList(List<String> users) {
        Platform.runLater(() -> {
            userList.getItems().clear();
            userList.getItems().addAll(users);
        });
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

    @FXML
    private TextArea messageTextArea;

    @FXML
    private ImageView imageView;

    @FXML
    private void sendImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);



        }
    }

    public static void main(String[] args) {
        launch(args);
    }

     

}