package fr.eni.parking.bo;

import java.util.Objects;

public class Formateur implements java.io.Serializable {

    //region propriété

    private int id;
    private String nom ="";
    private String prenom="";

    //endregion

    //region constructeur

    /**
     * Constructeur de la classe "Formateur" sans l'id du formateur
     * @param nom
     * @param prenom
     */
    public Formateur(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }

    /**
     * Constructeur de la classe "Formateur" avec l'id du formateur
     * @param id
     * @param nom
     * @param prenom
     */
    public Formateur(int id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }
    public Formateur(int id) {
        this.id = id;
    }
    public Formateur(){

    }

    //endregion

    //region fonction

    /**
     * récupère l'id du formateur
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * modifie l'id du formateur
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * récupère le nom du formateur
     * @return nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * modifie le nom du formateur
     * @param nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * récupère le nom du formateur
     * @return Prenom
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * modifie le prenom du formateur
     * @param prenom
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    //endregion

    //region overwrite
    @Override
    public String toString() {
        return nom +"   "+ prenom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Formateur formateur = (Formateur) o;
        return id == formateur.id &&
                nom.equals(formateur.nom) &&
                prenom.equals(formateur.prenom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, prenom);
    }
    //endregion





}
