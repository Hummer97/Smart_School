package com.bhartiyamonline.smart_school.Models;

public class ClassData {
    private String id;
    private String school_id;
    private String class_name;
    private String active;
    private String created_at;
    private String updated_at;

    public ClassData() {

    }

    public ClassData(String id, String school_id, String class_name, String active, String created_at, String updated_at) {
        this.id = id;
        this.school_id = school_id;
        this.class_name = class_name;
        this.active = active;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchool_id() {
        return school_id;
    }

    public void setSchool_id(String school_id) {
        this.school_id = school_id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
