package org.example;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class PrimaryController {
    @FXML
    private TextField campoNombre;
    @FXML
    private TextField campoStock;
    @FXML
    private ListView<Producto> listViewProductos;
    private ObservableList<Producto> listaProductos = FXCollections.observableArrayList();


    @FXML
    public void initialize() {
        listaProductos.add(new Producto("Laptop", 999.99, 5));
        listaProductos.add(new Producto("Teléfono", 299.99, 10));
        listaProductos.add(new Producto("Tablet", 199.99, 8));
        listaProductos.add(new Producto("Cámara", 149.99, 15));
        listaProductos.add(new Producto("Auriculares", 49.99, 20));
        listaProductos.add(new Producto("Teclado", 29.99, 30));
        listaProductos.add(new Producto("Mouse", 19.99, 25));
        listaProductos.add(new Producto("Monitor", 199.99, 12));
        listaProductos.add(new Producto("Impresora", 149.99, 8));
        listaProductos.add(new Producto("Altavoces", 79.99, 18));
        listaProductos.add(new Producto("Micrófono", 39.99, 15));
        listaProductos.add(new Producto("Disco Duro", 79.99, 10));
        listaProductos.add(new Producto("Memoria RAM", 49.99, 22));
        listaProductos.add(new Producto("Router", 39.99, 17));
        listaProductos.add(new Producto("Tarjeta Gráfica", 129.99, 7));
        listaProductos.add(new Producto("Batería Externa", 29.99, 25));
        listaProductos.add(new Producto("Mochila para Laptop", 34.99, 20));
        listaProductos.add(new Producto("Funda para Teléfono", 14.99, 30));
        listaProductos.add(new Producto("Cable USB", 9.99, 40));
        listaProductos.add(new Producto("Adaptador de Corriente", 19.99, 15));


        listViewProductos.setItems(listaProductos);
    }
    @FXML
    public void buscar(){
        String nombre = campoNombre.getText();
        int stock = 0;
        try {
            stock = Integer.parseInt(campoStock.getText());
        }catch (Exception e){
        }

        int finalStock = stock;
        ObservableList<Producto> listaFiltrada = listaProductos.filtered(producto -> producto.contieneNombre(nombre) &&
                producto.contieneStoc(finalStock));


        listViewProductos.setItems(listaFiltrada);
    }
}
