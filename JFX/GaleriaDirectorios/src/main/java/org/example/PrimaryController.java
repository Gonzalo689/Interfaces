package org.example;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class PrimaryController {
    @FXML
    private GridPane panel;
    @FXML
    private Button atras;

    @FXML
    public void initialize(){
        File file = new File(System.getProperty("user.home"));
        creacion(file);
    }
    public void creacion(File directorio){
        int columna = 0;
        int fila = 0;
        int n;
        File[] files = directorio.listFiles();
        for (File file: files) {
            String[] s = file.getName().split("\\.");
            file.getName();
            if(file.isDirectory()){
                n = file.listFiles() == null ? 0 : -1;
                if( n == 0){
                    panel.add(new Button(file.getName()), columna, fila);
                }else{
                    Button b = addButton(file);
                    panel.add(b, columna, fila);
                }
            }
            else if (s.length >= 2 &&
                    (s[s.length -1 ].equals("png") || s[s.length -1 ].equals("jpg")|| s[s.length -1 ].equals("jpeg")|| s[s.length -1 ].equals("gif"))) {
                ImageView i = crearImageView(file);
                panel.add(i,columna,fila);
            }
            else if(file.isFile()){
                Label l = new Label(file.getName());
                panel.add(l,columna,fila);
            }

            columna++;
            if (columna > 3){
                columna = 0 ;
                fila++;
            }


        }
    }
    public Button addButton(File directorio){
        Button b = new Button(directorio.getName());
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                panel.getChildren().clear();
                creacion(directorio);
                botonAtras(directorio);

            }
        });
        return b;
    }
    public void botonAtras(File Directori){
        atras.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                panel.getChildren().clear();
                creacion(Directori.getParentFile());
                botonAtras(Directori.getParentFile());

            }
        });
    }
    public  ImageView crearImageView(File file){
        ImageView imagen = new ImageView();
        Image i = new Image("file:" + file.getAbsoluteFile());
        imagen.setImage(i);
        imagen.setFitHeight(150);
        imagen.setFitWidth(150);
        imagen.setPreserveRatio(true);
        return imagen;
    }

}
