package com.example.javaproject.entities;

public class Card {

    private int id;
    private int patient_id;

    public Card(int id, int patient_id) {
        this.id = id;
        this.patient_id = patient_id;
    }

    public Card(int patient_id) {
        this.patient_id = patient_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }
}
