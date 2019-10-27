package models;

public class Location {

    private String cin_emp,location;

    public Location() {
    }

    public Location(String cin_emp, String location) {
        this.cin_emp = cin_emp;
        this.location = location;
    }

    public String getCin_emp() {
        return cin_emp;
    }

    public void setCin_emp(String cin_emp) {
        this.cin_emp = cin_emp;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
