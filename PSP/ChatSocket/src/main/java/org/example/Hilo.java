package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.List;

public class Hilo implements Runnable{
    List<String> messages;
    TextField textField;
    ObservableList<String>listMessage;
    ListView<String> listView;


    public Hilo(List<String> messages, ObservableList<String>listMessage, TextField textField,ListView<String> listView){
        this.messages = messages;
        this.listMessage=listMessage;
        this.textField=textField;
        this.listView = listView;
    }
    @Override
    public void run() {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(12345);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        byte[] reciveData = new byte[1024];
        DatagramPacket recivPacket = new DatagramPacket(reciveData, reciveData.length);
        while (true) {
            try {
                socket.receive(recivPacket);
            } catch (IOException e) {
                socket.close();
                e.printStackTrace();
            }
            byte[] data = recivPacket.getData();
            String message = new String(data, 0, recivPacket.getLength());
            messages.add(message);
            listMessage = FXCollections.observableArrayList(messages);
            listView.setItems(listMessage);
            listView.getSelectionModel().selectLast();
        }

    }

}
