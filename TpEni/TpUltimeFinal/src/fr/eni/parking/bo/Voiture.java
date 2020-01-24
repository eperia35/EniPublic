package fr.eni.parking.bo;

import jdk.nashorn.internal.runtime.options.Options;

import java.util.Objects;

public class Voiture implements java.io.Serializable{

    //region propriété

    private int id;
    private String nom;
    private String pi ;
    private Formateur formateur;

    //endregion

    //region constructeur
    /**
     * Constructeur de la classe "Voiture" avec l'id
     * @param id
     * @param nom
     * @param pi
     */
    public Voiture(int id, String nom, String pi,Formateur formateur) {

        // TODO
        this.nom = nom;
        this.pi = pi;
        this.formateur = formateur;
        this.id = id;
    }

    /**
     * Constructeur de la classe"Voiture" sans l'id
     * @param nom
     * @param pi
     */
    public Voiture(String nom, String pi,Formateur formateur) {
        this.nom = nom;
        this.pi = pi;
        this.formateur = formateur;
    }

    public Voiture() {
        this.formateur = null;
    }
    //endregion

    //region fonction
    /**
     * récupère l'id de la voiture
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * modifie l'id de la voiture
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * récupère le nom de la voiture
     * @return nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * modifie le nom de la voiture
     * @param nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * récupère la pi de la voiture
     * @return pi
     */
    public String getPi() {
        return pi;
    }

    /**
     * modifie la pi de la voiture
     * @param pi
     */
    public void setPi(String pi) {
        this.pi = pi;
    }

    /**
     * récupère le formateur lié à la voiture
     * @return formateur
     */
    public Formateur getFormateur() {
        return formateur;
    }

    /**
     * modifie le formateur lié à la voiture
     * @param formateur
     */
    public void setFormateur(Formateur formateur) {
        this.formateur = formateur;
    }

    //endregion

    //region overwrite

    @Override
    public String toString() {
        return "Voiture{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", pi='" + pi + '\'' +
                ", formateur=" + formateur +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voiture voiture = (Voiture) o;
        return id == voiture.id &&
                nom.equals(voiture.nom) &&
                pi.equals(voiture.pi) &&
                formateur.equals(voiture.formateur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, pi, formateur);
    }
    //endregion






}
