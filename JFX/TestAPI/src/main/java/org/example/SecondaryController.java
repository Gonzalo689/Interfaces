package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SecondaryController {
    @FXML
    Label titleLabel;
    @FXML
    Label synopsisLabel;
    @FXML
    Label startDateLabel;
    @FXML
    Label ageRatingLabel ;
    @FXML
    Label episodeCountLabel ;
    @FXML

    Label showTypeLabel;
    @FXML
    ImageView imageView ;

    public void setSecondaryController(String titleLabel, String synopsisLabel, String startDateLabel, String ageRatingLabel, int episodeCountLabel, String showTypeLabel, String imageView) {
        this.titleLabel.setText(titleLabel);
        this.synopsisLabel.setText(synopsisLabel);
        styleSynopsis();
        this.startDateLabel.setText("Start Date: " + startDateLabel);
        this.ageRatingLabel.setText("Age Rating: " + ageRatingLabel);
        this.episodeCountLabel.setText("Episodes: " + episodeCountLabel);
        this.showTypeLabel.setText("Show Type: " + showTypeLabel);
        this.imageView.setImage(new Image(imageView));
        styleDatosImageView();
    }
    public void styleDatosImageView(){
        this.imageView.setFitWidth(200);
        this.imageView.setFitHeight(280);
    }
    public void styleSynopsis(){
        synopsisLabel.setWrapText(true);
        synopsisLabel.setMaxWidth(400);
    }


}