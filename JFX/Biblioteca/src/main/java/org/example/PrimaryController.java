package org.example;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class PrimaryController {
    @FXML
    private TableView<Libro> tableView;
    @FXML
    private TextField fieldTitulo;
    @FXML
    private TextField fieldAutor;
    @FXML
    private TextField fieldGenero;
    @FXML
    private Button botonAgregar;
    @FXML
    private Button botonActualizar;
    @FXML
    private Button botonEliminar;
    private ObservableList<Libro> listaLibros = FXCollections.observableArrayList();
    public static LibroRepository lr = new LibroRepository();

    public void initialize() {
        lr.crearConexion();
        cargarColumnas();
        cargaDatos();
        botonAgregar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                crearLibro();
                cargaDatos();
                limpiarCampoTexto();
            }
        });
        botonActualizar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                actualizarLibro();
                cargaDatos();
                limpiarCampoTexto();
            }
        });
        botonEliminar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                eliminarLibro();
                cargaDatos();
                limpiarCampoTexto();
            }
        });
    }
    public void cargarColumnas(){
        TableColumn<Libro, String> colNTitulo = new TableColumn<>("Título");
        TableColumn<Libro, Double> colAutor = new TableColumn<>("Autor");
        TableColumn<Libro, Integer> colGenero = new TableColumn<>("Género");
        TableColumn<Libro, Double> colPrestado = new TableColumn<>("Prestado");
        tableView.getColumns().addAll(colNTitulo, colAutor, colGenero,colPrestado);
        colNTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colPrestado.setCellValueFactory(new PropertyValueFactory<>("prestado"));
        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Libro>() {
            @Override
            public void changed(ObservableValue<? extends Libro> observableValue, Libro oldValue, Libro newValue) {
                if (newValue != null) {
                    fieldTitulo.setText(newValue.getTitulo());
                    fieldAutor.setText(newValue.getAutor());
                    fieldGenero.setText(newValue.getGenero());
                }
            }
        });
    }
    public void cargaDatos(){
        listaLibros.clear();
        listaLibros.addAll(lr.leerDatos());
        tableView.setItems(listaLibros);
    }
    public void crearLibro(){
        String titulo = fieldTitulo.getText();
        String autor = fieldAutor.getText();
        String genero = fieldGenero.getText();
        if (!titulo.isEmpty() && !autor.isEmpty() && !genero.isEmpty() && !libroRepe(titulo,autor))
            lr.crearLibro(titulo,autor,genero);
    }
    public boolean libroRepe(String titulo, String autor){
        for (Libro l:listaLibros) {
            if (l.getAutor().equals(autor) && l.getTitulo().equals(titulo)){
                return true;
            }
        }
        return false;
    }
    public void actualizarLibro(){
        Libro l = tableView.getSelectionModel().getSelectedItem();
        String titulo = fieldTitulo.getText();
        String autor = fieldAutor.getText();
        String genero = fieldGenero.getText();
        if (l!=null)
            if (l.getTitulo().equals(titulo) && l.getAutor().equals(autor))
                lr.actualizarLibro(l.getId(),titulo,autor,genero);
            else if (!libroRepe(titulo,autor)) {
                lr.actualizarLibro(l.getId(),titulo,autor,genero);
            }
    }
    public void eliminarLibro(){
        Libro l = tableView.getSelectionModel().getSelectedItem();
        if (l!=null)
            lr.eliminarLibro(l.getId());
    }
    public void limpiarCampoTexto(){
        fieldTitulo.setText("");
        fieldAutor.setText("");
        fieldGenero.setText("");
    }
}
