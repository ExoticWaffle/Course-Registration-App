package main.java.server.models;

import java.io.Serializable;

/**
 * Objet représentant un formulaire d'inscription et contient le prénom, nom, email, et la matricule de l'étudiant ainsi que le cours auquel il s'inscrit. Implémente l'interface Serializable.
 */
public class RegistrationForm implements Serializable {
    private String prenom;
    private String nom;
    private String email;
    private String matricule;
    private Course course;

    /**
     * Crée un formulaire d'inscription selon les informations indiqués en paramètres
     * @param prenom
     * @param nom
     * @param email
     * @param matricule
     * @param course
     */
    public RegistrationForm(String prenom, String nom, String email, String matricule, Course course) {
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.matricule = matricule;
        this.course = course;
    }

    /**
     * retourne le prénom de l'étudiant
     * @return le prénom de l'étudiant
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * change le prénom de l'étudiant au prénom indiqué en paramètres
     * @param prenom le nouveau prénom
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * retourne le nom de l'étudiant
     * @return le nom de l'étudiant
     */
    public String getNom() {
        return nom;
    }

    /**
     * change le nom de l'étudiant au nom indiqué en paramètres
     * @param nom le nouveau nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * retourne l'adresse courriel de l'étudiant
     * @return l'adresse courriel de l'étudiant
     */
    public String getEmail() {
        return email;
    }

    /**
     * change l'adresse courriel de l'étudiant à l'adresse indiqué en paramètres
     * @param email la nouvelle adresse courriel
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * retourne la matricule de l'étudiant
     * @return la matricule de l'étudiant
     */
    public String getMatricule() {
        return matricule;
    }

    /**
     * change la matricule de l'étudiant à la matricule indiqué en paramètres
     * @param matricule la nouvelle matricule
     */
    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    /**
     * retourne le cours auquel l'étudiant veut s'inscrire
     * @return le cours auquel l'étudiant veut s'inscrire
     */
    public Course getCourse() {
        return course;
    }

    /**
     * change le cours auquel l'étudiant veut s'inscrire au cours indiqué en paramètres
     * @param course le nouveau cours
     */
    public void setCourse(Course course) {
        this.course = course;
    }

    /**
     * convertit une instance de la classe à une chaîne de caractères
     * @return la chaîne de caractères
     */
    @Override
    public String toString() {
        return "InscriptionForm{" + "prenom='" + prenom + '\'' + ", nom='" + nom + '\'' + ", email='" + email + '\'' + ", matricule='" + matricule + '\'' + ", course='" + course + '\'' + '}';
    }
}
