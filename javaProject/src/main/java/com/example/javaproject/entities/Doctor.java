package com.example.javaproject.entities;

public class Doctor {
    private int id;
    private String name;
    private String category;
    private String password;
    private String role;

    public Doctor(int id, String name, String category, String role, String password) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.role = role;
        this.password = password;
    }

    public Doctor(String name, String category, String role, String password) {
        this.name = name;
        this.category = category;
        this.role = role;
        this.password = password;
    }

    public Doctor() {

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
