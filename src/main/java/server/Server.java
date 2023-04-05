package main.java.server;

import javafx.util.Pair;
import main.java.server.models.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Un serveur pour le système d'inscription aux cours à UdeM. Il peut accepter des clients et répondre à ses requêtes.
 */
public class Server {

    /**
     * REGISTER_COMMAND Le mot clé pour la requête 'inscription'
     */
    public final static String REGISTER_COMMAND = "INSCRIRE";
    /**
     * LOAD_COMMAND Le mot clé pour la requête 'charger'
     */
    public final static String LOAD_COMMAND = "CHARGER";
    private final ServerSocket server;
    private Socket client;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private final ArrayList<EventHandler> handlers;

    /**
     * Crée un serveur selon le port indiqué
     * @param port
     * @throws IOException
     */
    public Server(int port) throws IOException {
        this.server = new ServerSocket(port, 1);
        this.handlers = new ArrayList<EventHandler>();
        this.addEventHandler(this::handleEvents);
    }

    /**
     * Ajoute un EventHandler pour une nouvelle type d'événement
     * @param h Le EventHandler à ajouter
     */
    public void addEventHandler(EventHandler h) {
        this.handlers.add(h);
    }

    private void alertHandlers(String cmd, String arg) {
        for (EventHandler h : this.handlers) {
            h.handle(cmd, arg);
        }
    }

    /**
     *  connecte le client au serveur, répond à ses requêtes, et le déconnecte. Ces trois étapes sont éxécutée de manière répétitive. Cette fonction continue de s'executer tant qu'il n y a pas d'erreur.
     */
    public void run() {
        while (true) {
            try {
                client = server.accept();
                System.out.println("Connecté au client: " + client);
                objectInputStream = new ObjectInputStream(client.getInputStream());
                objectOutputStream = new ObjectOutputStream(client.getOutputStream());
                listen();
                disconnect();
                System.out.println("Client déconnecté!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * gère la requête du client s'il y en a un
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void listen() throws IOException, ClassNotFoundException {
        String line;
        if ((line = this.objectInputStream.readObject().toString()) != null) {
            Pair<String, String> parts = processCommandLine(line);
            String cmd = parts.getKey();
            String arg = parts.getValue();
            this.alertHandlers(cmd, arg);
        }
    }

    /**
     * Sépare une commande en deux parties: la type de commande et les arguments
     * @param line la ligne de commande en format String
     * @return une paire clé-valeur, avec la type de commande comme clé et les arguments comme valeur
     */
    public Pair<String, String> processCommandLine(String line) {
        String[] parts = line.split(" ");
        String cmd = parts[0];
        String args = String.join(" ", Arrays.asList(parts).subList(1, parts.length));
        return new Pair<>(cmd, args);
    }

    /**
     * Déconnecte le client du serveur
     * @throws IOException
     */
    public void disconnect() throws IOException {
        objectOutputStream.close();
        objectInputStream.close();
        client.close();
    }

    /**
     * gère une commande selon la type et les arguments indiqué en paramètres
     * @param cmd la type de commande
     * @param arg les arguments
     */
    public void handleEvents(String cmd, String arg) {
        if (cmd.equals(REGISTER_COMMAND)) {
            handleRegistration();
        } else if (cmd.equals(LOAD_COMMAND)) {
            handleLoadCourses(arg);
        }
    }

    /**
     Lire un fichier texte contenant des informations sur les cours et les transofmer en liste d'objets 'Course'.
     La méthode filtre les cours par la session spécifiée en argument.
     Ensuite, elle renvoie la liste des cours pour une session au client en utilisant l'objet 'objectOutputStream'.
     La méthode gère les exceptions si une erreur se produit lors de la lecture du fichier ou de l'écriture de l'objet dans le flux.
     @param arg la session pour laquelle on veut récupérer la liste des cours
     */
    public void handleLoadCourses(String arg) {
        try{
            BufferedReader reader = new BufferedReader(new FileReader("src/main/java/server/data/cours.txt"));
            ArrayList<Course> courseList = new ArrayList<>();
            String line;

            while((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                if(parts[2].equals(arg)){
                    courseList.add(new Course(parts[1], parts[0], parts[2]));
                }
            }
            Course[] courses = courseList.toArray(new Course[courseList.size()]);
            objectOutputStream.writeObject(courses);
            reader.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     Récupérer l'objet 'RegistrationForm' envoyé par le client en utilisant 'objectInputStream', l'enregistrer dans un fichier texte
     et renvoyer un message de confirmation au client.
     La méthode gére les exceptions si une erreur se produit lors de la lecture de l'objet, l'écriture dans un fichier ou dans le flux de sortie.
     */
    public void handleRegistration() {
        try {
            RegistrationForm form = (RegistrationForm) objectInputStream.readObject();

            BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/server/data/inscription.txt", true));
            Course course = form.getCourse();
            String s = "\n" + course.getSession() + "\t" + course.getCode() + "\t" + form.getMatricule() + "\t" +
                    form.getPrenom() + "\t" + form.getNom() + "\t" + form.getEmail();
            writer.append(s);
            writer.close();
            objectOutputStream.writeObject("Félicitations! Inscription réussie de "+ form.getPrenom() +" au cours "+ course.getCode() +".");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}

