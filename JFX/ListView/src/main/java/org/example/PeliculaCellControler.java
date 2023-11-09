package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PeliculaCellControler {
    @FXML private Label titulo;
    @FXML private Label director;
    @FXML private Label anioSalida;
    @FXML private ImageView urlImagen;

    public void setPeliculaCellControler(String titulo, String director, int anioSalida, String urlImagen) {
        this.titulo.setText(titulo);
        this.director.setText("- Dirigida por " +director);
        this.anioSalida.setText("(" + anioSalida + ")");
        if (!urlImagen.isEmpty()){
            this.urlImagen.setImage(new Image(urlImagen));
            this.urlImagen.setFitWidth(100);
            this.urlImagen.setPreserveRatio(true);
        }
    }

}

