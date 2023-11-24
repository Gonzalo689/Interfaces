package org.example;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.example.apimodels.AnimeData;
import org.example.apimodels.Datum;

import java.io.IOException;

public class PrimaryController {

    public ListView<Datum> listView;
    public Button buscar;

    public TextField limitField;
    public TextField offsetField;
    public int limit;
    public int ofset;


    public void initialize() {
        limit = 10;
        ofset = 10;
        cambiarAnimes();

        buscar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                if (!limitField.getText().isEmpty() && !offsetField.getText().isEmpty()){
                    try {
                        limit = Integer.parseInt(limitField.getText());
                        ofset = Integer.parseInt(offsetField.getText());
                        cambiarAnimes();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });


    }
    public void cambiarAnimes(){
        KitsuAPIClient client = new KitsuAPIClient();
        try {
            AnimeData animes = client.getAnimes(limit, ofset);
            listView.setItems(FXCollections.observableList(animes.data));

            listView.setCellFactory(new Callback<>() {
                @Override
                public ListCell<Datum> call(ListView<Datum> datumListView) {
                    return new ListCell<>() {
                        @Override
                        protected void updateItem(Datum datum, boolean empty) {
                            super.updateItem(datum, empty);

                            if (datum == null || empty) {
                                setText(null);
                                setGraphic(null);
                            } else {
                                String title = datum.attributes.canonicalTitle;
                                String posterImage = datum.attributes.posterImage.medium;
                                String synopsis = datum.attributes.synopsis;
                                String startDate = datum.attributes.startDate;
                                String ageRating = datum.attributes.ageRating;
                                int episodeCount = datum.attributes.episodeCount;
                                String showType = datum.attributes.showType;
                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("secondary.fxml"));
                                    Parent root = loader.load();
                                    SecondaryController controller = loader.getController();

                                    controller.setSecondaryController(title, synopsis, startDate, ageRating, episodeCount, showType, posterImage);
                                    setGraphic(root);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    };
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
