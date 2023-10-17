package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;

public class PrimaryController {

    @FXML
    private MenuItem btnAbrir;
    @FXML
    private TextArea textArea;
    @FXML
    private MenuBar menuArchivo;
    @FXML
    public void initialize(){

        textArea.setText("Hola");

    }
}
