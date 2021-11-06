package com.app.rmlprototype.entity;

import javax.persistence.*;

@Entity
@Table(name = "diagnosiscenter")
public class DiagnosisCenter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int id;

    @Column(name = "name")
    public String name;

    @Column(name = "registration_number")
    public String registrationNumber;

    @Column(name = "username")
    public String username;

    @Column(name = "password")
    public String password;

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

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    @Override
    public String toString() {
        return "DiagnosisCenter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
