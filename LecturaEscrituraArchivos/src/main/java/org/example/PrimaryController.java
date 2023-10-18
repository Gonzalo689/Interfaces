package org.example;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class PrimaryController {

    @FXML
    private BorderPane panel;
    @FXML
    private TextArea textArea;
    @FXML
    private MenuItem menuAbrir;
    @FXML
    private MenuItem menuGuardar;
    @FXML
    private Button limpiar;

    @FXML
    public void initialize(){

        menuAbrir.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                File file = accederArchivo();
                try {
                    textArea.setText("");
                    java.util.Scanner scanner = new java.util.Scanner(file);
                    while (scanner.hasNextLine()) {
                        textArea.appendText(scanner.nextLine() + "\n");
                    }
                    scanner.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        menuGuardar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                File file = accederArchivo();
                try {
                    FileWriter newFile = new FileWriter(file);
                    newFile.write(textArea.getText());
                    newFile.close();
                    textArea.setText("");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });
        limpiar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                textArea.setText("");
            }
        });

    }

    public File accederArchivo(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Abra el archivo");
        return fileChooser.showOpenDialog( panel.getScene().getWindow());
    }
}
