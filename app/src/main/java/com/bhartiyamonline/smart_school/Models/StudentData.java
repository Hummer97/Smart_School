package com.bhartiyamonline.smart_school.Models;

public class StudentData {
    private String id,school_id,name,register_no,
            roll_no,phone_no,alt_mobile_no,class_id,p_name,dob,
            category,section,image,active,attendence_percent,
            created_at,updated_at,class_name,type,present,absent,half_time;

    public StudentData(String id, String school_id, String name, String register_no, String roll_no, String phone_no, String alt_mobile_no, String class_id, String p_name, String dob, String category, String section, String image, String active, String attendence_percent, String created_at, String updated_at, String class_name, String type, String present, String absent, String half_time) {
        this.id = id;
        this.school_id = school_id;
        this.name = name;
        this.register_no = register_no;
        this.roll_no = roll_no;
        this.phone_no = phone_no;
        this.alt_mobile_no = alt_mobile_no;
        this.class_id = class_id;
        this.p_name = p_name;
        this.dob = dob;
        this.category = category;
        this.section = section;
        this.image = image;
        this.active = active;
        this.attendence_percent = attendence_percent;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.class_name = class_name;
        this.type = type;
        this.present = present;
        this.absent = absent;
        this.half_time = half_time;
    }

    public StudentData() {

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

    public String getRegister_no() {
        return register_no;
    }

    public void setRegister_no(String register_no) {
        this.register_no = register_no;
    }

    public String getRoll_no() {
        return roll_no;
    }

    public void setRoll_no(String roll_no) {
        this.roll_no = roll_no;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getAlt_mobile_no() {
        return alt_mobile_no;
    }

    public void setAlt_mobile_no(String alt_mobile_no) {
        this.alt_mobile_no = alt_mobile_no;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
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

    public String getAttendence_percent() {
        return attendence_percent;
    }

    public void setAttendence_percent(String attendence_percent) {
        this.attendence_percent = attendence_percent;
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

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }

    public String getAbsent() {
        return absent;
    }

    public void setAbsent(String absent) {
        this.absent = absent;
    }

    public String getHalf_time() {
        return half_time;
    }

    public void setHalf_time(String half_time) {
        this.half_time = half_time;
    }
}
