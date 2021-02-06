package com.bhartiyamonline.smart_school.Models;

public class Data {
    private String moblie_no;
    private boolean isSelected;

    public Data(String moblie_no, boolean isSelected) {
        this.moblie_no = moblie_no;
        this.isSelected = isSelected;
    }

    public Data() {
    }

    public String getMoblie_no() {
        return moblie_no;
    }

    public void setMoblie_no(String moblie_no) {
        this.moblie_no = moblie_no;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
