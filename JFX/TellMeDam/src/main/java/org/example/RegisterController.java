package org.example;

import java.io.IOException;

import com.fasterxml.jackson.databind.util.ISO8601Utils;
import io.github.palexdev.materialfx.controls.MFXProgressBar;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.example.api.APICallback;
import org.example.api.UserAPIClient;
import org.example.api.model.User;

public class RegisterController {
    private UserAPIClient userApi = new UserAPIClient();
    @FXML
    private TextField emailRegister;
    @FXML
    private TextField passwordRegister;
    @FXML
    private TextField usernameRegister;
    @FXML
    private TextField confirmRegister;
    @FXML
    private MFXProgressBar progressSpinner ;
    public void initialize() {
        progressSpinner.visibleProperty().setValue(false);
        enter(emailRegister);
        enter(passwordRegister);
        enter(usernameRegister);
        enter(confirmRegister);
    }
    public void registro() throws IOException, InterruptedException {
        progressSpinner.visibleProperty().setValue(true);
        String email = emailRegister.getText();
        String contrasena= passwordRegister.getText();
        String unsername = usernameRegister.getText();
        String confirm = confirmRegister.getText();
        if(!confirmtext())
            if(confirmEmail(email))
                if(isValidPassword(contrasena,confirm)){
                    userApi.register(email, unsername, contrasena, new APICallback() {
                        @Override
                        public void onSuccess(Object response) throws IOException {
                            App.userRegister = (User) response;
                            App.setRoot("chat",500, 600) ;
                            System.out.println(response);
                            progressSpinner.visibleProperty().setValue(false);


                        }
                        @Override
                        public void onError(Object error) {
                        }
                    });

                }else
                    showAlert("Error", "Las contraseñas no coinciden");
            else
                showAlert("Error", "Email no valido");
        else
            showAlert("Error", "Campo/s Vacios");

    }
    public boolean confirmEmail(String value){
        return value.contains("@") && value.contains(".") ;
    }
    public boolean confirmtext(){
        return emailRegister.getText().isEmpty() || passwordRegister.getText().isEmpty() ||
                usernameRegister.getText().isEmpty() || confirmRegister.getText().isEmpty();
    }
    private boolean isValidPassword(String password, String password2) {
        return password.length() >= 8 && password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\",./<>?].*") && password.equals(password2);
    }

    private void showAlert(String title, String content) {
        progressSpinner.visibleProperty().setValue(false);

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void enter(TextField textField){
        TextField tf = textField;
        tf.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        System.out.println("enter");
                        registro();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("login",450, 500);
    }
}