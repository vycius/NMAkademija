package com.nmakademija.nmaakademija.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by NMA on 2016.08.23.
 */
public class User implements Serializable {
    private int ID;
    private String name;
    private String email;
    private String phone;
    private String bio;
    private String photoURL;
    private ArrayList<Role> roles;

    public User() {}

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public ArrayList<Role> getRoles() {
        return roles;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
