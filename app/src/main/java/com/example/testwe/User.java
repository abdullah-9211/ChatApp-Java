package com.example.testwe;

public class User {

    String id;
    String name;
    String lname;
    String email;
    String bio;
    String password;
    String picture;
    String lastMessage;

    public User(){

    }

    public User(String id, String name, String lname, String email, String bio, String password, String picture, String lastMessage) {
        this.id = id;
        this.name = name;
        this.lname = lname;
        this.email = email;
        this.bio = bio;
        this.password = password;
        this.picture = picture;
        this.lastMessage = lastMessage;
    }

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

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}