package main.java.server;

/**
 * Démarre le serveur.
 */
public class ServerLauncher {
    public final static int PORT = 1337;

    /**
     * Un fonction main. Ça démarre le serveur.
     * @param args arguments de la ligne de commande
     */
    public static void main(String[] args) {
        Server server;
        try {
            server = new Server(PORT);
            System.out.println("Server is running...");
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}