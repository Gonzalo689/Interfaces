package org.example;

import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class FileTreeItem extends TreeItem<String> {
    private File file;

    public FileTreeItem(File filee){
        super(filee.getName());
        this.file = filee;
        if(file.isDirectory()){
            this.setGraphic(crearImagen("/folder.png"));
        }else {
            this.setGraphic(crearImagen("/file.png"));
        }

    }

    public ImageView crearImagen(String fileName){
        ImageView imagen = new ImageView();
        Image i = new Image(getClass().getResourceAsStream(fileName));
        imagen.setImage(i);
        imagen.setFitHeight(20);
        imagen.setPreserveRatio(true);
        return imagen ;
    }

    public File getFile() {
        return file;
    }
}
