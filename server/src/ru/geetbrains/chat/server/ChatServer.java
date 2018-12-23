package ru.geetbrains.chat.server;

import ru.geetbrains.chat.network.TCPConnection;
import ru.geetbrains.chat.network.TCPConnectionListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ChatServer implements TCPConnectionListener {

    public static void main(String[] args) {
        new ChatServer();
    }

    private final ArrayList<TCPConnection> connections = new ArrayList<>();

    private ChatServer() {
        System.out.println("Server running...");
        try (ServerSocket serverSocket = new ServerSocket(8189)) {
            while (true) {
                try {
                    new TCPConnection(this, serverSocket.accept());
                } catch (IOException e) {
                    System.out.println("TCPConnection exception: " + e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public synchronized void onConnectionReady(TCPConnection tcpcOnnection) {
        connections.add(tcpcOnnection);
        sendToAllConnections("Client connected: " + tcpcOnnection );
    }

    @Override
    public synchronized void onReceiveString(TCPConnection tcpcOnnection, String value) {
        sendToAllConnections(value);
    }

    @Override
    public synchronized void onDisconnect(TCPConnection tcpcOnnection) {
        connections.remove(tcpcOnnection);
        sendToAllConnections("Client disconnected: " + tcpcOnnection);
    }

    @Override
    public synchronized void onException(TCPConnection tcpcOnnection, Exception e) {
        System.out.println("TCPConnection exception: " + e);
    }

    private void sendToAllConnections(String value) {
        System.out.println(value);
        for (TCPConnection connection : connections
        ) {
            connection.sendString(value);
        }
    }
}
