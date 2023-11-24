package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.example.api.APICallback;
import org.example.api.UserAPIClient;
import org.example.api.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * JavaFX App
 */
public class App extends Application {
    public static User userRegister;
    public static Scene scene;
    public static Stage s;

    public static UserAPIClient userApiALL = new UserAPIClient(); // API
    public static ArrayList<User> arrayListAllUsers = getAllUsers() ; // lista de todos lo usuarios

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"), 450, 500);
        s = stage;
        s.setScene(scene);
        s.show();
    }

    static void setRoot(String fxml,double width, double height) throws IOException {
        scene.setRoot(loadFXML(fxml));
        scene.getWindow().setWidth(width);
        scene.getWindow().setHeight(height);
        s.setMaximized(false);
        centerStageOnScreen(scene.getWindow());
    }
    private static void centerStageOnScreen(Window window) {
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();

        window.setX((screenWidth - window.getWidth()) / 2);
        window.setY((screenHeight - window.getHeight()) / 2);
    }

    public static ArrayList<User> getAllUsers(){
        ArrayList<User> arrayListAllUser = new ArrayList<>();
        try {
            userApiALL.getAllUsers(new APICallback() {
                @Override
                public void onSuccess(Object response) throws IOException {
                    List<User> userList = (List<User>) response;
                    arrayListAllUser.addAll(userList);
                }
                @Override
                public void onError(Object error) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayListAllUser;
    }
    static void goChats() throws IOException {
        scene.setRoot(loadFXML("chat"));
        s.setMaximized(true);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}