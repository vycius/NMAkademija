package com.nmakademija.nmaakademija.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String bio;
    private String image;
    private int section;
    private ArrayList<Role> roles;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public ArrayList<Role> getRoles() {
        return roles;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getSection() {
        return section;
    }
}
