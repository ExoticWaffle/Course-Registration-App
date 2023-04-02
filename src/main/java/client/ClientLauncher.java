package main.java.client;

import main.java.client.UI.*;

import java.io.IOException;

public class ClientLauncher {
    private final static int PORT = 1337;

    public static void main(String[] args){
        CLI ui;
        try{
            ui = new CLI("127.0.0.1", PORT);
            ui.run();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
