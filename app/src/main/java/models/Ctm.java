package models;

public class Ctm {


    private int id;
    private String numBon,prix,destination;
    private String accompagne;

    public Ctm() {
    }

    public Ctm(String numBon, String prix, String destination) {
        this.numBon = numBon;
        this.prix = prix;
        this.destination = destination;
    }

    public Ctm(int id, String numBon, String prix, String destination) {
        this.id = id;
        this.numBon = numBon;
        this.prix = prix;
        this.destination = destination;
    }

    public Ctm(int id, String numBon, String prix,String destination, String accompagne) {
        this.id = id;
        this.numBon = numBon;
        this.prix = prix;
        this.destination = destination;
        this.accompagne = accompagne;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumBon() {
        return numBon;
    }

    public void setNumBon(String numBon) {
        this.numBon = numBon;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getAccompagne() {
        return accompagne;
    }
    public void setAccompagne(String accompagne) {
        this.accompagne = accompagne;
    }
}
