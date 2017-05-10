package com.rodrigoftw.myworkouttracker.myworkouttracker.model;

/**
 * Created by Rodrigo on 01/05/2017.
 */
public class User {
    private int id;
    private String name;
    private String email;
    private int type;
    private String uid;
    private String image;
    private String phone;

    public User() {
    }

    public User(int id, String name, String email, int type, String uid, String image, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.type = type;
        this.uid = uid;
        this.image = image;
        this.phone = phone;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
