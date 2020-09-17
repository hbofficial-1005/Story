package com.demo.story.model;

public class user {
    private String Name;
    private String password;
    private String phone;

    public user() {
    }

    public user(String Name, String password, String phone) {
        this.Name = Name;
        this.password = password;
        this.phone = phone;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
