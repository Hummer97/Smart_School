package com.bhartiyamonline.smart_school.Models;

public class DashBoardData {

    private String Name;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCount() {
        return Count;
    }

    public void setCount(String count) {
        Count = count;
    }

    public DashBoardData(String name, String count) {
        Name = name;
        Count = count;
    }

    private String Count;

}
