package com.techelevator.model;

public class Ingredient {

    private int id;
    private String name;
    private double measurement;
    private String unit;

    public Ingredient() {}

    public Ingredient(int id, String name, double measurement, String unit) {
        this.id = id;
        this.name = name;
        this.measurement = measurement;
        this.unit = unit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMeasurement() {
        return measurement;
    }

    public void setMeasurement(double measurement) {
        this.measurement = measurement;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String type) {
        this.unit = type;
    }
}
