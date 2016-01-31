package com.anthonydenaud.server;

import com.anthonydenaud.common.Packet;
import com.anthonydenaud.common.PacketType;
import org.apache.commons.io.IOUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class SRPServer {

    private DataOutputStream outputSteam;
    private ServerSocket server;
    private Socket socket;
    private Thread receiveThread;
    private boolean runReceiveThread;

    boolean bigLogSended = false;

    public SRPServer() throws IOException {
        server = new ServerSocket(32330);

        while (true) {
            socket = server.accept();

            runReceiveThread = true;
            beginReceive();

        }
    }

    private void beginReceive() {
        receiveThread = new Thread(new Runnable() {
            @Override
            public void run() {
                receive();
            }
        });
        receiveThread.setName("ReceiverThread");
        receiveThread.start();
    }

    private void receive() {
        if (runReceiveThread) {
            try {

                InputStream inputStream = socket.getInputStream();
                byte[] request = new byte[4096];
                int result = inputStream.read(request, 0, request.length);

                if (result > 0) {
                    handleRequest(request);
                }

                receive();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void handleRequest(byte[] request) throws IOException {
        Packet packet = new Packet(request);


        System.out.println("Receive : " + packet.toString());

        if (packet.getType() == PacketType.SERVERDATA_AUTH.getValue()) {

            String password = packet.getBody();

            Packet responsePacket = new Packet();
            responsePacket.setType(PacketType.SERVERDATA_AUTH_RESPONSE.getValue());

            if (password.equals("password")) {
                responsePacket.setId(packet.getId());
            } else {
                packet.setId(-1);
            }
            send(responsePacket);
        }
        if (packet.getType() == PacketType.SERVERDATA_EXECCOMMAND.getValue()) {
            dispatchRequest(packet.getId(), packet.getBody());
        }
    }

    private void dispatchRequest(int id, String request) throws IOException {
        if (request.equals("ListPlayers")) {
            Packet packet = new Packet(id, PacketType.SERVERDATA_RESPONSE_VALUE.getValue(), MockServer.getPlayerList());
            send(packet);
        }
        if (request.equals("getgamelog")) {
            Packet packet;
            if(!bigLogSended){
                 packet = new Packet(id, PacketType.SERVERDATA_RESPONSE_VALUE.getValue(), MockServer.getbiggamelog());
                bigLogSended = true;
            }else{
                 packet = new Packet(id, PacketType.SERVERDATA_RESPONSE_VALUE.getValue(), MockServer.getgamelog());
            }
            send(packet);
        }
        if (request.equals("DestroyWildDinos")) {
            Packet packet = new Packet(id, PacketType.SERVERDATA_RESPONSE_VALUE.getValue(), MockServer.destroyWildDinos());
            send(packet);
        }
    }
    private void send(Packet packet) throws IOException {
        OutputStream outputSteam = new DataOutputStream(socket.getOutputStream());
        outputSteam.write(packet.encode());
    }
}
