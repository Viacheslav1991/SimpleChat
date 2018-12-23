package ru.geetbrains.chat.network;

public interface TCPConnectionListener {

    void onConnectionReady(TCPConnection tcpcOnnection);
    void onReceiveString(TCPConnection tcpcOnnection, String value);
    void onDisconnect(TCPConnection tcpcOnnection);
    void onException(TCPConnection tcpcOnnection, Exception e);

}
