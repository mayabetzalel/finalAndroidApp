package com.example.class3demo2.model;

public class User {
    private String email;
    private String name;
    private String photoURL;

    public User(String email, String name, String photoURL) {
        this.email = email;
        this.name = name;
        this.photoURL = photoURL;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }
}
