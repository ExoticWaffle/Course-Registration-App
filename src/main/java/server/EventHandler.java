package main.java.server;

/**
 * EventHandler est une interface fonctionnelle utilisé dans Server.java pour répondre aux commandes du client. Un EventHandler peut être implémenté pour chaque type d'événement, et le code dans handle() est executé lorsque l'événement se produit.
 */
@FunctionalInterface
public interface EventHandler {
    void handle(String cmd, String arg);
}
