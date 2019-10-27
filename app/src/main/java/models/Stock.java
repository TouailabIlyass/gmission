package models;

public class Stock {

    private double somme;
    private String type;
    private final static String VTT="VTT";
    private final static String VTA="VTA";

    public Stock() {
    }

    public Stock(double somme, String type) {
        this.somme = somme;
        this.type = type;
    }

    public double getSomme() {
        return somme;
    }

    public void setSomme(double somme) {
        this.somme = somme;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static String getVTT() {
        return VTT;
    }

    public static String getVTA() {
        return VTA;
    }
}
