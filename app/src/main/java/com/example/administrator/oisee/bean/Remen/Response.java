package com.example.administrator.oisee.bean.Remen;

public class Response {
    private int userid;
    private String avatar;
    private String nick_name;
    private int page_view;
    private int is_focus_on;
    public void setUserid(int userid) {
        this.userid = userid;
    }
    public int getUserid() {
        return userid;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getAvatar() {
        return avatar;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }
    public String getNick_name() {
        return nick_name;
    }

    public void setPage_view(int page_view) {
        this.page_view = page_view;
    }
    public int getPage_view() {
        return page_view;
    }

    public void setIs_focus_on(int is_focus_on) {
        this.is_focus_on = is_focus_on;
    }
    public int getIs_focus_on() {
        return is_focus_on;
    }
}
