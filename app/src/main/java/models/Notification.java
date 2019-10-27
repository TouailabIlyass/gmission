package models;

public class Notification {

    private int id;
    private String cin_emp,prix,type;

    public Notification() {
    }

    public Notification(int id, String cin_emp, String prix, String type) {
        this.id = id;
        this.cin_emp = cin_emp;
        this.prix = prix;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCin_emp() {
        return cin_emp;
    }

    public void setCin_emp(String cin_emp) {
        this.cin_emp = cin_emp;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
