package com.bhartiyamonline.smart_school.Models;

public class TotalStudentData {
    private String name;
    private String mobile_no;
    private String img;
    private boolean flag;
    //private boolean checked=false;


    public TotalStudentData()
    {

    }



//    public TotalStudentData(String name, String mobile_no, boolean checked) {
//        this.name = name;
//        this.mobile_no = mobile_no;
//        this.checked = checked;
//    }

    public TotalStudentData(String name, String mobile_no, String img, boolean flag) {
        this.name = name;
        this.mobile_no = mobile_no;
        this.img = img;
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

//    public boolean isChecked() {
//        return checked;
//    }
//
//    public void setChecked(boolean checked) {
//        this.checked = checked;
//    }
}
