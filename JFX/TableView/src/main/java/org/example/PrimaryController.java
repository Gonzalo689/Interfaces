package org.example;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class PrimaryController {

    @FXML
    private TableView<Producto> tableView;
    @FXML
    private TextField busqueda;
    private ObservableList<Producto> listaProducto = FXCollections.observableArrayList();

    public void initialize() {
       cargarColumnas();
       aniadirProductos();
       tableView.setItems(listaProducto);
    }
    public void cargarColumnas(){
        TableColumn<Producto, String> colNombre = new TableColumn<>("Nombre");
        TableColumn<Producto, Double> colPrecio = new TableColumn<>("Precio");
        TableColumn<Producto, Integer> colStock = new TableColumn<>("Stock");
        TableColumn<Producto, Double> colPeso = new TableColumn<>("Peso");
        TableColumn<Producto, LocalDate> colFechaNacimiento = new TableColumn<>("Fecha de Vencimiento");
        TableColumn<Producto, String> colColor = new TableColumn<>("Color");
        TableColumn<Producto, String> colMaterial = new TableColumn<>("Material");
        TableColumn<Producto, String> colNumMaterial = new TableColumn<>("Numero del Material");
        tableView.getColumns().addAll(colNombre, colPrecio, colStock,colPeso,colFechaNacimiento,colColor,colMaterial,colNumMaterial);
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
        colFechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fechaVencimiento"));
        colColor.setCellValueFactory(new PropertyValueFactory<>("color"));
        colMaterial.setCellValueFactory(new PropertyValueFactory<>("material"));
        colNumMaterial.setCellValueFactory(new PropertyValueFactory<>("numMaterial"));
    }
    public void aniadirProductos(){
        listaProducto.add( new Producto("Laptop", 999.99, 20, 2.5, new Date(),
                "Gris", "Plástico", "ABC123"));
        listaProducto.add(new Producto("Smartphone", 599.99, 50, 0.3, new Date(),
                "Negro", "Metal", "XYZ789"));
        listaProducto.add(new Producto("Televisor", 799.99, 15, 12.0, new Date(),
                "Negro", "Plástico", "DEF456"));
        listaProducto.add(new Producto("Zapatillas", 49.99, 100, 0.5, new Date(),
                "Blanco", "Tela", "123GHI"));
        listaProducto.add(new Producto("Refrigerador", 799.99, 10, 50.0, new Date(),
                "Plateado", "Metal", "456JKL"));
        listaProducto.add(new Producto("Cámara Digital", 299.99, 30, 0.2, new
                Date(), "Rojo", "Plástico", "MNO789"));
        listaProducto.add(new Producto("Reloj", 199.99, 25, 0.1, new Date(), "Oro",
                "Metal", "PQR012"));
        listaProducto.add( new Producto("Bicicleta", 349.99, 5, 12.0, new Date(),
                "Azul", "Metal", "STU345"));
        listaProducto.add(new Producto("Libro", 19.99, 200, 0.5, new Date(), "N/A",
                "Papel", "VWX678"));
        listaProducto.add(new Producto("Impresora", 149.99, 12, 10.0, new Date(),
                "Blanco", "Plástico", "YZA901"));
        listaProducto.add(new Producto("Auriculares", 39.99, 30, 0.2, new Date(),
                "Negro", "Plástico", "BCD234"));
        listaProducto.add(new Producto("Teclado inalámbrico", 29.99, 25, 0.5, new
                Date(), "Blanco", "Plástico", "EFG567"));
        listaProducto.add(new Producto("Silla de oficina", 79.99, 15, 10.0, new
                Date(), "Negro", "Cuero", "HIJ890"));
        listaProducto.add(new Producto("Cafetera", 49.99, 20, 3.0, new Date(),
                "Plateado", "Metal", "KLM123"));
        listaProducto.add(new Producto("Horno de microondas", 69.99, 10, 15.0, new
                Date(), "Blanco", "Metal", "NOP456"));
        listaProducto.add(new Producto("Pelota de fútbol", 19.99, 50, 0.7, new
                Date(), "Blanco y negro", "Piel sintética", "QRS789"));
        listaProducto.add(new Producto("Gafas de sol", 59.99, 40, 0.1, new Date(),
                "Negro", "Plástico", "TUV012"));
        listaProducto.add(new Producto("Mesa de comedor", 149.99, 8, 20.0, new
                Date(), "Marrón", "Madera", "WXY345"));
        listaProducto.add(new Producto("Batería recargable", 29.99, 60, 0.3, new
                Date(), "Verde", "Metal", "YZB678"));
        listaProducto.add(new Producto("Cepillo de dientes", 4.99, 100, 0.05, new
                Date(), "Azul", "Plástico", "CDE901"));
    }
    @FXML
    public void buscar(){
        String nombre = busqueda.getText();
        ObservableList<Producto> listaFiltrada = listaProducto.filtered(producto -> producto.contieneNombre(nombre) );
        tableView.setItems(listaFiltrada);
    }

}
