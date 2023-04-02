package main.java.client;

import main.java.server.models.Course;
import main.java.server.models.RegistrationForm;

import java.io.IOException;
import java.util.Scanner;

public class CLI {

    private Client client;
    public CLI(String ip, int port) throws IOException {
        client = new Client(ip, port);
    }

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

            System.out.print("Veuillez saisir votre prénom: ");
            String firstName = reader.next();
            System.out.print("Veuillez saisir votre nom: ");
            String lastName = reader.next();
            System.out.print("Veuillez saisir votre email: ");
            String email = reader.next();
            System.out.print("Veuillez saisir votre matricule: ");
            String matricule = reader.next();

            Course course = new Course("", "", "");
            while(true){
                System.out.print("Veuillez saisir le code du cours: ");
                String courseCode = reader.next();
                for(Course c : courses) {
                    if (courseCode.equals(c.getCode())) {
                        course = c;
                        break;
                    }
                }
                if(!course.getName().equals("")){
                    break;
                }
                System.out.println(courseCode + " n'est pas un cours valide.");
            }

            System.out.println(client.sendRegistration(new RegistrationForm(firstName, lastName, email, matricule, course)));
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

}
