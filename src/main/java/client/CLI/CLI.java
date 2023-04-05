package main.java.client.CLI;

import main.java.client.Client;
import main.java.server.models.Course;

import java.io.IOException;
import java.util.Scanner;

/**
 * Le client de ligne de commande.
 */
public class CLI {

    private final Client client;

    /**
     * Crée un client selon l'adresse IP et port indiqué.
     * @param ip l'adresse IP
     * @param port le port du serveur auquel le client se connecte
     * @throws IOException
     */
    public CLI(String ip, int port) throws IOException {
        client = new Client(ip, port);
    }

    /**
     * Exécute tous les fonctionnalités du client. Prend l'input de l'utilisateur et y affiche des messages, envoie des requêtes au serveur et en reçois des réponses.
     * @throws IOException
     */
    public void run() throws IOException{
        Scanner reader = new Scanner(System.in);
        System.out.println("***Bienvenu au portail d'inscription de l'UDEM***");
        while(true){
            System.out.println("Veuillez choisir la session pour laquelle vous voulez consulter la liste des cours");
            System.out.print("1. Automne\n2. Hiver\n3. Été\n>Choix: ");
            String session = num2session(choice());
            Course[] courses = client.getCourses(session);

            System.out.println("Les cours offerts pendant la session d'"+ session.toLowerCase() +" sont:");
            for(int i = 0; i < courses.length; i++){
                Course course = courses[i];
                System.out.println(i+1 +". " + course.getCode() + "    " + course.getName());
            }

            System.out.println("Vous avez les options suivants: ");
            System.out.print("1. Consulter les cours pour une autre session\n2. Insciption à un cours \n> Choix: ");
            if(choice() == 1){
                continue;
            }

            String msg;
            while(true){
                System.out.print("Veuillez saisir votre prénom: ");
                String firstName = reader.next();
                System.out.print("Veuillez saisir votre nom: ");
                String lastName = reader.next();
                System.out.print("Veuillez saisir votre email: ");
                String email = reader.next();
                System.out.print("Veuillez saisir votre matricule: ");
                String matricule = reader.next();
                System.out.print("Veuillez saisir le code du cours: ");
                String courseCode = reader.next();
                msg = client.sendRegistration(firstName, lastName, email, matricule, getCourse(courseCode, courses));
                System.out.println(msg);
                if(msg.charAt(0) == 'F' || msg.charAt(0) == 'E'){
                    break;
                }
            }
            System.out.print("1. Inscription à un autre cours\n2. Quitter\n> Choix: ");
            if(choice() == 2){
                break;
            }
        }
    }

    private String num2session(int choice){
        String session;
        switch (choice) {
            case 1 -> session = "Automne";
            case 2 -> session = "Hiver";
            case 3 -> session = "Ete";
            default -> session = "";
        }
        return session;
    }

    private int choice(){
        Scanner reader = new Scanner(System.in);
        while(true) {
            String choice = reader.next();
            if (isValid(choice, 3)) {
                return Integer.parseInt(choice);
            }
            System.out.print("Veuillez saisir un choix valide: ");
        }
    }

    private boolean isValid(String input, int max){
        int n;
        if (input == null) {
            return false;
        }
        try {
            n = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return false;
        }
        return n > 0 && n <= max;
    }

    private Course getCourse(String code, Course[] courses){
        for(Course c : courses) {
            if (code.equals(c.getCode())) {
                return c;
            }
        }
        return null;
    }

}
