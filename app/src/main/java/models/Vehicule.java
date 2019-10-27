package models;

import java.util.ArrayList;

public class Vehicule {

    private int id;
    private String marque,distance,destination,conducteur_cin;
    private ArrayList<String> accompagne;

    public Vehicule() {
    }

    public Vehicule(int id, String marque, String distance, String destination, String conducteur_cin, ArrayList<String> accompagne) {
        this.id = id;
        this.marque = marque;
        this.distance = distance;
        this.destination = destination;
        this.conducteur_cin = conducteur_cin;
        this.accompagne = accompagne;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getConducteur_cin() {
        return conducteur_cin;
    }

    public void setConducteur_cin(String conducteur_cin) {
        this.conducteur_cin = conducteur_cin;
    }

    public ArrayList<String> getAccompagne() {
        return accompagne;
    }

    public void setAccompagne(ArrayList<String> accompagne) {
        this.accompagne = accompagne;
    }

}
