package com.anthonydenaud.server;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MockServer {

    public static String getgamelog(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return "Server received, But no response!!";
    }

    public  static  String getPlayerList() throws IOException {
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("playerList.txt");
        return IOUtils.toString(stream);
    }
    public static String destroyWildDinos(){
        return "OK";
    }

    public static String getbiggamelog() throws IOException {
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("log.txt");
        return IOUtils.toString(stream);
    }
}
