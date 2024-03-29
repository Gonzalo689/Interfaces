module org.example {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.fasterxml.jackson.databind;
    requires okhttp3;
    requires com.google.gson;
    requires okhttp3.logging;
    requires java.net.http;
    requires MaterialFX;

    opens org.example.api to com.google.gson, javafx.fxml;
    opens org.example to javafx.fxml;
    opens org.example.api.model to com.google.gson, javafx.fxml;
    exports org.example;
    exports org.example.api;
}
