package org.example;

import java.io.IOException;

import io.github.palexdev.materialfx.controls.MFXProgressBar;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.example.api.APICallback;
import org.example.api.UserAPIClient;
import org.example.api.model.Error;
import org.example.api.model.User;
public class LoginController {
    private UserAPIClient userApi = new UserAPIClient();

    @FXML
    private TextField emailLogin;
    @FXML
    private TextField contrasenaLogin;
    @FXML
    private MFXProgressBar progressSpinner ;
    public void initialize() {
        progressSpinner.visibleProperty().setValue(false);
        enter(emailLogin);
        enter(contrasenaLogin);

    }

    public void login() throws IOException, InterruptedException {
        progressSpinner.visibleProperty().setValue(true);
        String email = emailLogin.getText();
        String contrasena= contrasenaLogin.getText();
        if (!errors()){
            userApi.login(email, contrasena, new APICallback() {
                @Override
                public void onSuccess(Object response) throws IOException {
                    if (response instanceof User){
                        App.userRegister = (User) response;
                        App.goChats();
                        progressSpinner.visibleProperty().setValue(false);
                    }
                }

                @Override
                public void onError(Object error) {
                    if (error instanceof Error){
                        String e= ((Error)error).getError();
                        showAlert("Error",e);                    }
                }
            });
        }
    }
    public boolean errors(){
        if(emailLogin.getText().isEmpty() || contrasenaLogin.getText().isEmpty() ){
            showAlert("Error","Campo/s sin rellenar");

            return true;
        }
        return false;
    }
    private void showAlert(String title, String content) {
        progressSpinner.visibleProperty().setValue(false);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
        progressSpinner.visibleProperty().setValue(false);
    }

    @FXML
    private void switchToRegister() throws IOException {
        App.setRoot("register",500, 600) ;

    }
    private void enter(TextField textField){
        TextField tf = textField;
        tf.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        System.out.println("enter");
                        login();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }


}
