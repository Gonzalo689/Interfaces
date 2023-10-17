package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.opencsv.CSVWriter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

public class PrimaryController {
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtTelefono;
    @FXML
    private ListView tablaIntereses;
    @FXML
    private DatePicker dpFechaNacimiento;


    @FXML
    public void initialize(){
        tablaIntereses.getItems().addAll("Deportes", "Música", "Viajes", "Cine", "Libros");

        dpFechaNacimiento.setConverter(new StringConverter<>() {
            final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });


    }

    @FXML
    private void guardar(){
        String fecha = String.valueOf(dpFechaNacimiento.getValue());
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter("contactos.csv", true))) {

            String[] datos = {txtNombre.getText(), txtEmail.getText(), txtTelefono.getText(),
                    fecha,  tablaIntereses.getSelectionModel().getSelectedItem().toString()};
            csvWriter.writeNext(datos);
        } catch (IOException e) {
            e.printStackTrace();
        }

        limpiarControles();
    }
    private void limpiarControles(){
        txtNombre.clear();
        txtEmail.clear();
        txtTelefono.clear();
        dpFechaNacimiento.setValue(null);
        tablaIntereses.getSelectionModel().clearSelection();
    }
    @FXML
    private void cancelar(){
        Platform.exit();
    }
}
