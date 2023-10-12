package com.example.ead;

import java.util.List;

public class Student {
    private String id;
    private String name;

    private String gender;
    private int age;

    private String username;

    private String password;

    // Constructors, getters, and setters for the fields

    // Constructors (if needed)
    public Student() {
    }

    public Student(String id, String name, String gender, int age) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    public Student(String updatedName, int updatedAge, String updatedGender) {
        this.name = updatedName;
        this.gender = updatedGender;
        this.age = updatedAge;
    }

    public void updateStudent(String name, String gender, int age) {
        this.name = name;
        this.gender = gender;
        this.age = age;
    }


    // Getters and setters for the fields
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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
