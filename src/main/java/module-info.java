module com.example.chatroom {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.chatroom to javafx.fxml;
    exports com.example.chatroom;
}