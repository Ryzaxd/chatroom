module com.example.chatroom {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    exports com.example.chatroom.Client;
    exports com.example.chatroom.Server;
}