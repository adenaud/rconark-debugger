package com.anthonydenaud.client;

import com.anthonydenaud.common.PlayerInfo;
import com.anthonydenaud.common.ServerInfo;
import org.apache.commons.lang3.ArrayUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueryClient implements ConnectionListener {

    private QueryConnection connection;

    public QueryClient() throws IOException {
        connection = new QueryConnection();
        connection.setConnectionListener(this);
        connection.openConnection("212.83.166.35", 27017);
    }

    @Override
    public void onConnect() {
        getServInfo();
        getPlayers();

    }

    private ServerInfo getServInfo() {

        ServerInfo serverInfo = new ServerInfo();

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayOutputStream.write(getNull());
            byteArrayOutputStream.write(0x54);
            byteArrayOutputStream.write("Source Engine Query".getBytes());
            byteArrayOutputStream.write(0x00);
            byte[] response = connection.send(byteArrayOutputStream.toByteArray());

            byte[] nameBytes;
            byte[] mapBytes;
            byte[] folderBytes;
            byte[] gameBytes;
            byte[] idBytes;//2 bytes
            byte[] playersBytes; // 1 byte
            byte[] maxPlayersBytes; // 1 byte
            byte[] botBytes; // 1 byte
            byte[] serverTypeBytes; // 1 byte
            byte[] environmentBytes; // 1 byte
            byte[] visibilityBytes; // 1 byte
            byte[] vacBytes; // 1 byte

            int offset = 6;
            int textLength = getTextLength(response, offset);
            nameBytes = Arrays.copyOfRange(response, offset, offset + textLength - 1);
            offset += textLength;

            textLength = getTextLength(response, offset);
            mapBytes = Arrays.copyOfRange(response, offset, offset + textLength - 1);
            offset += textLength;

            textLength = getTextLength(response, offset);
            folderBytes = Arrays.copyOfRange(response, offset, offset + textLength - 1);
            offset += textLength;

            textLength = getTextLength(response, offset);
            gameBytes = Arrays.copyOfRange(response, offset, offset + textLength - 1);
            offset += textLength;

            idBytes = Arrays.copyOfRange(response, offset, offset + 2);
            offset += 2;

            playersBytes = Arrays.copyOfRange(response, offset, offset + 1);
            offset++;

            maxPlayersBytes = Arrays.copyOfRange(response, offset, offset + 1);
            offset++;

            botBytes = Arrays.copyOfRange(response, offset, offset + 1);
            offset++;

            serverTypeBytes = Arrays.copyOfRange(response, offset, offset + 1);
            offset++;


            environmentBytes = Arrays.copyOfRange(response, offset, offset + 1);
            offset++;


            visibilityBytes = Arrays.copyOfRange(response, offset, offset + 1);
            offset++;

            vacBytes = Arrays.copyOfRange(response, offset, offset + 1);

            ArrayUtils.reverse(idBytes);
            short id = ByteBuffer.wrap(idBytes).getShort();
            serverInfo = new ServerInfo(new String(nameBytes),
                    new String(mapBytes),
                    new String(folderBytes),
                    new String(gameBytes),
                    id,
                    playersBytes[0],
                    maxPlayersBytes[0],
                    botBytes[0],
                    (char) serverTypeBytes[0],
                    (char) environmentBytes[0],
                    visibilityBytes[0],
                    vacBytes[0]);


        } catch (IOException e) {
            e.printStackTrace();
        }
        return serverInfo;
    }

    private List<PlayerInfo> getPlayers() {

        List<PlayerInfo> players = new ArrayList<>();

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayOutputStream.write(getNull());
            byteArrayOutputStream.write(0x55);
            byteArrayOutputStream.write(getNull());
            byte[] response = connection.send(byteArrayOutputStream.toByteArray());

            byte[] challenge = Arrays.copyOfRange(response, 5, 9);

            byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayOutputStream.write(getNull());
            byteArrayOutputStream.write(0x55);
            byteArrayOutputStream.write(challenge);
            response = connection.send(byteArrayOutputStream.toByteArray());


            byte[] nbPlayersBytes;
            nbPlayersBytes = Arrays.copyOfRange(response, 5, 6);
            int nbPlayers = nbPlayersBytes[0];

            int offset = 6;

            for (int i = 0; i < nbPlayers; i++) {
                byte[] indexBytes;
                byte[] playerNameBytes;
                byte[] durationBytes;

                indexBytes = Arrays.copyOfRange(response, offset, offset + 1);
                offset++;
                int playerLength = getTextLength(response, offset);
                playerNameBytes = Arrays.copyOfRange(response, offset, offset + playerLength - 1);
                offset += playerLength;
                offset += 4; // score
                durationBytes = Arrays.copyOfRange(response, offset, offset + 4);
                offset += 4; //Duration

                ArrayUtils.reverse(durationBytes);
                PlayerInfo player = new PlayerInfo(indexBytes[0], new String(playerNameBytes), ByteBuffer.wrap(durationBytes).getFloat());
                players.add(player);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return players;
    }

    private int getTextLength(byte[] response, int offset) {
        int textLength = 0;
        byte c = (byte) 0xFF;
        while (c != 0x00) {
            c = response[offset + textLength];
            textLength++;
        }
        return textLength;
    }

    private byte[] getNull() {
        byte[] header = new byte[4];
        Arrays.fill(header, (byte) 0xFF);
        return header;
    }
}
