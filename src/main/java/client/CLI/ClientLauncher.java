package main.java.client.CLI;

import java.io.IOException;

/**
 * Démarre le client de ligne de commande.
 */
public class ClientLauncher {
    private final static int PORT = 1337;

    /**
     * La méthode main qui démarre le client.
     * @param args
     */
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
