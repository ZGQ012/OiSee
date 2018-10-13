package com.example.administrator.oisee.bean;

public class Response {

    private int nationality_id;
    private String title;
    private String area_code;
    public void setNationality_id(int nationality_id) {
        this.nationality_id = nationality_id;
    }
    public int getNationality_id() {
        return nationality_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }
    public String getArea_code() {
        return area_code;
    }

}