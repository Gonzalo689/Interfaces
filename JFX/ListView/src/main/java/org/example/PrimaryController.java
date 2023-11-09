package org.example;

import java.text.SimpleDateFormat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class PrimaryController {

    @FXML
    private ListView<Pelicula> listView;

    @FXML
    public void initialize(){
        listView.setCellFactory(x -> new CustomFXMListCell());
        insertarDatos();

    }
    public class CustomFXMListCell extends ListCell<Pelicula> {
        @Override
        protected void updateItem(Pelicula pelicula, boolean empty) {
            super.updateItem(pelicula, empty);
            if (empty || pelicula == null) {
                setText(null);
                setGraphic(null);
            } else {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("peronalListView.fxml"));
                    Parent root = loader.load();
                    PeliculaCellControler controller = loader.getController();

                    controller.setPeliculaCellControler(pelicula.getTitulo(),pelicula.getDirector(),pelicula.getAnioSalida(),pelicula.getUrlImagen());
                    setGraphic(root);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void insertarDatos(){
        listView.getItems().add(new Pelicula("Forrest Gump", "Robert Zemeckis", 1994, ""));
        listView.getItems().add(new Pelicula("Pulp Fiction", "Quentin Tarantino", 1994, ""));
        listView.getItems().add(new Pelicula("El Señor de los Anillos: La Comunidad del Anillo", "Peter Jackson",
                2001, "https://es.web.img3.acsta.net/medias/nmedia/18/89/67/45/20061512.jpg"));
        listView.getItems().add(new Pelicula("Matrix", "Lana Wachowski, Lilly Wachowski", 1999, ""));
        listView.getItems().add(new Pelicula("Star Wars: Episodio IV - Una Nueva Esperanza", "George Lucas", 1977,
                "https://m.media-amazon.com/images/I/71C+KwExtCL._AC_UF894,1000_QL80_.jpg"));
        listView.getItems().add(new Pelicula("Jurassic Park", "Steven Spielberg", 1993, ""));
        listView.getItems().add(new Pelicula("Gladiador", "Ridley Scott", 2000, "https://resizing.flixster.com/-XZAfHZM39UwaGJIFWKAE8fS0ak=/v3/t/assets/p24674_p_v8_ae.jpg"));

    }
}
