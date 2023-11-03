package org.example;

import java.io.*;
import java.nio.charset.StandardCharsets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PrimaryController {
    @FXML
    private TreeView<String> treeView;
    @FXML
    private MenuItem guardar;
    @FXML
    private TabPane tabpanel;

    @FXML
    public void initialize(){
        File file = new File("D:\\Informatica2");
        FileTreeItem treeItemRoot = crearFileTree(file);
        treeView.setRoot(treeItemRoot);

        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                File fileSelect = ((FileTreeItem) newValue).getFile();
                if(fileSelect.isFile()){
                    int size = tabpanel.getTabs().size();
                    if(size == 0)
                        tabpanel.getTabs().add(0,crearTabs(fileSelect));
                    else
                        tabpanel.getTabs().add(size,crearTabs(fileSelect));
                    tabpanel.getSelectionModel().select(size);

                }
            }
        });

        guardar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String mensaje = "";
                if(tabpanel.getSelectionModel().isEmpty()){
                    mensaje = "No hay fichero selecionado";
                }else{
                    mensaje = "Archivo modificado";
                    guardarTexto();
                }
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setHeaderText(mensaje);
                a.setContentText(null);
                a.setTitle("OK");
                a.show();
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
    public Tab crearTabs(File f)
    {
        FileTab t = new FileTab(f, new TextArea(ficheroTexto(f)));
        return t;
    }
    public String ficheroTexto(File f){
        String texto="";
        try {
            BufferedInputStream bfw = new BufferedInputStream(new FileInputStream(f));
            texto = new String(bfw.readAllBytes(), StandardCharsets.UTF_8);
            bfw.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return texto;
    }
    public void guardarTexto(){
        try {
            File ficheroActual = ((FileTab) tabpanel.getSelectionModel().getSelectedItem()).getFile();
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(ficheroActual));
            String texto = ((TextArea) tabpanel.getSelectionModel().getSelectedItem().getContent()).getText();
            bos.write(texto.getBytes(StandardCharsets.UTF_8));
            bos.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
