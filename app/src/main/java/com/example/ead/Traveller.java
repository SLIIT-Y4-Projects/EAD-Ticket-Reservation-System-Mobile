package com.example.ead;

public class Traveller {

    private String username;
    private String fullName;
    private String password;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;


    public Traveller() {
    }
    public Traveller(String fullName, String username, String password) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}
