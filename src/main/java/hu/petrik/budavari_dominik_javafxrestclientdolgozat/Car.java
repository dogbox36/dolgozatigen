package hu.petrik.budavari_dominik_javafxrestclientdolgozat;

import com.google.gson.annotations.Expose;

import java.util.Date;

public class Car {
    private int id;
    @Expose
    private String Name;
    @Expose
    private String Fuel;
    @Expose
    private Boolean Electric;
    @Expose
    private Date Production;

    public Car(int id, String Name, String Fuel, Boolean Electric, Date Production) {
        this.id = id;
        this.Name = Name;
        this.Fuel = Fuel;
        this.Electric = Electric;
        this.Production = Production;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getFuel() {
        return Fuel;
    }

    public void setFuel(String fuel) {
        Fuel = fuel;
    }

    public Boolean getElectric() {
        return Electric;
    }

    public void setElectric(Boolean electric) {
        Electric = electric;
    }

    public Date getProduction() {
        return Production;
    }

    public void setProduction(Date production) {
        Production = production;
    }
}