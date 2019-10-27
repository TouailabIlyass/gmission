package models;

public class Transport {

    private int id,type;
    private String id_t;
    private String emp_cin;
    private Avion avion;
    private Train train;
    private Ctm ctm;
    private Vehicule vehicule;

    public Transport(int id, String id_t, int type, String emp_cin) {
        this.id = id;
        this.id_t = id_t;
        this.type = type;
        this.emp_cin = emp_cin;
    }



    public Transport(Avion avion) {
        this.avion = avion;
    }

    public Transport(Train train) {
        this.train = train;
    }

    public Transport(Ctm ctm) {
        this.ctm = ctm;
    }

    public Transport(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_t() {
        return id_t;
    }

    public void setId_t(String id_t) {
        this.id_t = id_t;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getEmp_cin() {
        return emp_cin;
    }

    public void setEmp_cin(String emp_cin) {
        this.emp_cin = emp_cin;
    }


    public Avion getAvion() {
        return avion;
    }

    public void setAvion(Avion avion) {
        this.avion = avion;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public Ctm getCtm() {
        return ctm;
    }

    public void setCtm(Ctm ctm) {
        this.ctm = ctm;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

}
