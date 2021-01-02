package com.bhartiyamonline.smart_school.Models;

public class TeacherData {
    private String id;
    private String school_id;
    private String name;
    private String dob;
    private String gender;
    private String email;
    private String phone;
    private String image;
    private String active;
    private String alt_phone_no;
    private String designation;
    private String address;
    private String joining_date;
    private String created_at;
    private String updated_at;

    public TeacherData(String id, String school_id, String name, String dob, String gender, String email, String phone, String image, String active, String alt_phone_no, String designation, String address, String joining_date, String created_at, String updated_at) {
        this.id = id;
        this.school_id = school_id;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.image = image;
        this.active = active;
        this.alt_phone_no = alt_phone_no;
        this.designation = designation;
        this.address = address;
        this.joining_date = joining_date;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public TeacherData() {

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public void setImage(String image) {
        this.image = image;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getAlt_phone_no() {
        return alt_phone_no;
    }

    public void setAlt_phone_no(String alt_phone_no) {
        this.alt_phone_no = alt_phone_no;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getJoining_date() {
        return joining_date;
    }

    public void setJoining_date(String joining_date) {
        this.joining_date = joining_date;
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
