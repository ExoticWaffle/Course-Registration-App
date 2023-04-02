package main.java.client;

import main.java.server.models.Course;
import main.java.server.models.RegistrationForm;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Client {
    private final String ip;
    private final int port;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;


    public Client(String ip, int port) throws IOException {
        this.ip = ip;
        this.port = port;
    }

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

    public String sendRegistration(RegistrationForm form){
        String msg = "";
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
