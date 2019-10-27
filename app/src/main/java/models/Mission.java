package models;

public class Mission {

    private int id;
    private String type,date,cin_emp;
    private Employe emp;

    public Mission() {
    }

    public Mission(int id, String type, String date, String cin_emp) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.cin_emp = cin_emp;
    }

    public Mission(int id, String type, String date, String cin_emp, Employe emp) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.cin_emp = cin_emp;
        this.emp = emp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCin_emp() {
        return cin_emp;
    }

    public void setCin_emp(String cin_emp) {
        this.cin_emp = cin_emp;
    }

    public Employe getEmp() {
        return emp;
    }

    public void setEmp(Employe emp) {
        this.emp = emp;
    }
}
