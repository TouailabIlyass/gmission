package models;

import java.io.Serializable;

public class Employe implements Serializable{

    private String cin,nom,prenom,grade,fonction;
    private int type;

    public Employe() {
    }

    public Employe(String cin, String nom, String prenom, String grade, String fonction,int type) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.grade = grade;
        this.fonction = fonction;
        this.type = type;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public int getType() { return type; }

    public void setType(int type) { this.type = type; }

}
