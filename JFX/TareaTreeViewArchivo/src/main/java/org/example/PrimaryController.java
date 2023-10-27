package org.example;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PrimaryController {
    @FXML
    private TreeView<String> treeView;
    @FXML
    private TextArea text;
    @FXML
    private TabPane tabpanel;

    @FXML
    public void initialize(){
        File file = new File("D:\\Informatica2");
        FileTreeItem treeItemRoot = crearFileTree(file);
        treeView.setRoot(treeItemRoot);
        tabpanel = new TabPane();


        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                File fileSelect = ((FileTreeItem) newValue).getFile();
                if(fileSelect.isFile()){
                    Tab newTab = crearTabs();
                    tabpanel.getTabs().add(newTab);

                    try {
                        BufferedInputStream bfw = new BufferedInputStream(new FileInputStream(fileSelect));
                        text.setText("");
                        text.setText(new String(bfw.readAllBytes(), StandardCharsets.UTF_8));
                        bfw.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public FileTreeItem crearFileTree(File directorio){
        FileTreeItem item = new FileTreeItem(directorio);
        for (File file: directorio.listFiles()) {
            if(file.isDirectory()){
                item.getChildren().add(crearFileTree(file));
            }
            else{
                item.getChildren().add(new FileTreeItem(file));
            }

        }
        return item;
    }
    public Tab crearTabs(){
        return new Tab("New Tab", new Label("Esta es una pestaña nueva"));
    }

}
