package main.java.client;

import main.java.server.models.Course;
import main.java.server.models.RegistrationForm;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Une classe qui contient des méthodes pour communiquer avec le serveur. Utilisé par le client de ligne de commande ainsi que le client graphique.
 */
public class Client {
    private final String ip;
    private final int port;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;


    /**
     * Crée une instance de la classe Client avec l'addresse IP et le port indiqué.
     * @param ip l'addresse IP
     * @param port le port du serveur avec lequel le client va communiquer
     * @throws IOException
     */
    public Client(String ip, int port) throws IOException {
        this.ip = ip;
        this.port = port;
    }

    /**
     * Communique avec le serveur et retourne la liste de cours disponibles pour la session indiqué.
     * @param session La session
     * @return la liste de cours disponibles
     */
    public Course[] getCourses(String session){
        Course[] courses = new Course[0];
        try{
            startConnection();
            String msg = "CHARGER "+session;
            out.writeObject(msg);
            courses = (Course[]) in.readObject();
            stopConnection();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return courses;
    }

    /**
     * Vérifie si les valeurs données en paramètres sont valides. Sinon, retourne un message String indiquant la valeur invalide. Si oui, envoie un formulaire d'inscription au serveur et retourne la réponse String du serveur (succès ou échec).
     * @param firstName
     * @param lastName
     * @param email
     * @param matricule
     * @param course
     * @return
     */
    public String sendRegistration(String firstName, String lastName, String email, String matricule, Course course){
        String msg = "";

        //prenom, nom validation
        if(firstName.equals("") || lastName.equals("")) {
            return "Veuillez remplir tous les champs.";
        }

        //email validation
        if(!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")){
            return "Veuillez saisir une adresse email valide.";
        }

        //matricule validation
        msg = "Votre matricule doit être composé de 8 chiffres.";
        try{
            int matInt = Integer.parseInt(matricule);
        }
        catch(NumberFormatException e){
            return msg;
        }
        if(matricule.length() != 8){
            return msg;
        }

        //course validation
        if(course == null){
            return "Veuillez choisir un cours valide.";
        }

        //send form
        RegistrationForm form = new RegistrationForm(firstName, lastName, email, matricule, course);
        try{
            startConnection();
            out.writeObject("INSCRIRE " + form.getCourse().getCode());
            out.writeObject(form);
            msg = in.readObject().toString();
            stopConnection();
        }
        catch(Exception e){
            msg = "Erreur. Votre requête n'a pas été effectué.";
        }
        return msg;
    }

    private void startConnection() throws IOException{
        socket = new Socket(ip, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    private void stopConnection() throws IOException{
        out.close();
        in.close();
        socket.close();
    }

}
