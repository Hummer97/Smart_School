package com.bhartiyamonline.smart_school.Models;

public class UserData {
    private String name,email,type,active,school_id;
    private int id;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getSchool_id() {
        return school_id;
    }

    public void setSchool_id(String school_id) {
        this.school_id = school_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserData(String name, String email, String type, String active, String school_id, int id) {
        this.name = name;
        this.email = email;
        this.type = type;
        this.active = active;
        this.school_id = school_id;
        this.id = id;
    }
}
