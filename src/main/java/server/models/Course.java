package main.java.server.models;

import java.io.Serializable;

/**
 * Object représentant un cours et contient son nom, code et la session dans laquelle le cours se déroule. Implémente l'interface Serializable.
 */
public class Course implements Serializable {

    private String name;
    private String code;
    private String session;

    /**
     * Crée un cours selon le nom, le code et la session indiqué en paramètres
     * @param name nom du cours
     * @param code code du cours
     * @param session session du cours
     */
    public Course(String name, String code, String session) {
        this.name = name;
        this.code = code;
        this.session = session;
    }

    /**
     * retourne le nom du cours
     * @return le nom du cours
     */
    public String getName() {
        return name;
    }

    /**
     * change le nom du cours au nom indiqué en paramètres
     * @param name le nouveau nom du cours
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * retourne le code du cours
     * @return le code du cours
     */
    public String getCode() {
        return code;
    }

    /**
     * change le code du cours au code indiqué en paramètres
     * @param code le nouveau code du cours
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * retourne la session du cours
     * @return la session du cours
     */
    public String getSession() {
        return session;
    }

    /**
     * change la session du cours à la session indiqué en paramètres
     * @param session la nouvelle session du cours
     */
    public void setSession(String session) {
        this.session = session;
    }

    /**
     * convertit une instance de la classe à une chaîne de caractères
     * @return la chaîne de caractères
     */
    @Override
    public String toString() {
        return "Course{" +
                "name=" + name +
                ", code=" + code +
                ", session=" + session +
                '}';
    }
}
