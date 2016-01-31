package com.anthonydenaud.server;

import java.io.IOException;

public class Server {
    public static void main( String[] args )
    {

        System.out.println( "Server starting ..." );
        try {

            new SRPServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
