package org.example;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClienteEnvioMss {
    public static void main(String[] args) {
        try {
            while (true) {
                DatagramSocket socket = new DatagramSocket();
                String message = "User2: Hola";
                byte[] sendData = message.getBytes();
                InetAddress address = InetAddress.getByName("127.0.0.1");
                int port = 12345;
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, port);
                socket.send(sendPacket);
                Thread.sleep(5000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
