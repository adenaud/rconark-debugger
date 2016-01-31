package com.anthonydenaud.client;

import java.io.IOException;
import java.net.*;

public class QueryConnection {
    private DatagramSocket socket;
    private Thread receiveThread;
    private ConnectionListener connectionListener;

    public void openConnection(String hostname, int port) throws IOException {
        socket = new DatagramSocket();
        socket.connect(new InetSocketAddress(hostname,port));
        System.out.println("connected");
        if(connectionListener != null){
            connectionListener.onConnect();
        }
    }

    public byte[] send(byte[] requestByteBuffer) throws IOException {
        System.out.println("sending packet");
        DatagramPacket request = new DatagramPacket(requestByteBuffer,requestByteBuffer.length);
        socket.send(request);

        byte[] responseByteBuffer = new byte[4096];
        DatagramPacket response = new DatagramPacket(responseByteBuffer,responseByteBuffer.length) ;
        socket.receive(response);
        return response.getData();
    }

    public void disconnect() throws IOException {
        socket.close();
        receiveThread.interrupt();
    }

    public void setConnectionListener(ConnectionListener connectionListener) {
        this.connectionListener = connectionListener;
    }
}
