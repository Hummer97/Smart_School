package com.bhartiyamonline.smart_school.Models;

public class SectionData {
    private String id,school_id,class_id,section,active,created_at,updated_at;

    public SectionData(String id, String school_id, String class_id, String section, String active, String created_at, String updated_at) {
        this.id = id;
        this.school_id = school_id;
        this.class_id = class_id;
        this.section = section;
        this.active = active;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public SectionData() {

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

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
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
