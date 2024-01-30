package org.example;

import java.lang.invoke.SwitchPoint;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.ChoiceBox;

import java.text.SimpleDateFormat;

public class PrimaryController {
    @FXML
    private BarChart<String,Number> barChart;
    @FXML
    private LineChart<String,Number> lineChart;
    @FXML
    private AreaChart<String,Number> areaChart;
    @FXML
    private ScatterChart<String,Number> scatterChart;
    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    public void initialize(){

        choiceBox.getItems().addAll("All","Precipitation","Temperature","Humidity","Wind Speed");
        choiceBox.setValue("All");
        choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setVisibleFalse();
            changeVisibility(newValue);
        });

        List<WeatherData> datos = WeatherData.getData();
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM");

        // Precipitation
        XYChart.Series<String, Number> precipitation = new XYChart.Series<>();
        precipitation.setName("Precipitation");
        for (WeatherData d :datos) {
            String monthName = monthFormat.format(d.getDate());
            precipitation.getData().add(new XYChart.Data<>(monthName,d.getPrecipitation()));
        }
        barChart.getData().add(precipitation);
        barChart.setTitle("Precipitation by Month");
        // Temperature
        XYChart.Series<String, Number> temperature = new XYChart.Series<>();
        temperature.setName("Temperature");
        for (WeatherData d :datos) {
            temperature.getData().add(new XYChart.Data<>(String.valueOf(d.getDate()),d.getMaxDegrees()));
        }
        lineChart.getData().add(temperature);
        lineChart.setTitle("Temperature Over Time");

        // Humidity
        XYChart.Series<String, Number> humidity = new XYChart.Series<>();
        humidity.setName("Humidity");
        for (WeatherData d :datos) {
            humidity.getData().add(new XYChart.Data<>(String.valueOf(d.getDate()),d.getHumidityPercentage()));
        }
        areaChart.getData().add(humidity);
        areaChart.setTitle("Humidity Over Time");

        // Humidity
        XYChart.Series<String, Number> windSpeed = new XYChart.Series<>();
        windSpeed.setName("Wind Speed");
        for (WeatherData d :datos) {
            windSpeed.getData().add(new XYChart.Data<>(String.valueOf(d.getDate()),d.getHumidityPercentage()));
        }
        scatterChart.getData().add(windSpeed);
        scatterChart.setTitle("Wind Speed Over Time");

    }
    public void changeVisibility(String value){
        switch (value) {
            case "All":
                setVisibleTrue();
                break;

            case "Precipitation":
                barChart.setVisible(true);
                break;
            case "Temperature":
                lineChart.setVisible(true);
                break;
            case "Humidity":
                areaChart.setVisible(true);
                break;
            case "Wind Speed":
                scatterChart.setVisible(true);
                break;
        }
    }
    public void setVisibleFalse(){
        barChart.setVisible(false);
        areaChart.setVisible(false);
        scatterChart.setVisible(false);
        lineChart.setVisible(false);
    }
    public void setVisibleTrue(){
        barChart.setVisible(true);
        areaChart.setVisible(true);
        scatterChart.setVisible(true);
        lineChart.setVisible(true);
    }


}
