package org.example;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class PrimaryController {

    @FXML
    private ListView<String> listView;
    private ObservableList<String> listMessage ;
    private List<String> message;
    @FXML
    private Button buttonSend;
    @FXML
    private TextField textField;
    @FXML
    public void initialize() throws IOException {
        message = new ArrayList<>();
        Hilo h = new Hilo(message,listMessage,textField,listView);
        Thread t = new Thread(h);
        t.start();
        buttonSend.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                enviarMensaje(textField.getText());
                textField.setText("");
            }
        });
        listView.setItems(listMessage);
    }
    public static void enviarMensaje(String messages) {
        try {
            DatagramSocket socket = new  DatagramSocket();
            String message = "User1: " + messages;
            byte[] sendData = message.getBytes();
            InetAddress address = InetAddress.getByName("127.0.0.1");
            int port = 12345;
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, port);
            socket.send(sendPacket);
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}



