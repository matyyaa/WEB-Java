package com.example.javaproject.entities;

public class Treatment {

    private int id;
    private String procedure;
    private String medicine;
    private String surgery;
    private int card_id;

    public Treatment(int id, String procedure, String medicine, String surgery, int card_id) {
        this.id = id;
        this.procedure = procedure;
        this.medicine = medicine;
        this.surgery = surgery;
        this.card_id = card_id;
    }

    public Treatment(String procedure, String medicine, String surgery, int card_id) {
        this.procedure = procedure;
        this.medicine = medicine;
        this.surgery = surgery;
        this.card_id = card_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProcedure() {
        return procedure;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getSurgery() {
        return surgery;
    }

    public void setSurgery(String surgery) {
        this.surgery = surgery;
    }

    public int getCard_id() {
        return card_id;
    }

    public void setCard_id(int card_id) {
        this.card_id = card_id;
    }
}
