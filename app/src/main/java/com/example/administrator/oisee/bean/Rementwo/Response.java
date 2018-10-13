package com.example.administrator.oisee.bean.Rementwo;

import java.util.List;

public class Response {
    private int dynamic_id;
    private int user_id;
    private int type;
    private String avatar;
    private String nick_name;
    private long add_time;
    private String locations;
    private String content;
    private List<String> image_list;
    private String video;
    private String voice;
    private int page_view;
    private int comment_count;
    private int thumbup_count;
    private int is_thumbup;
    private int is_focus_on;
    public void setDynamic_id(int dynamic_id) {
        this.dynamic_id = dynamic_id;
    }
    public int getDynamic_id() {
        return dynamic_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public int getUser_id() {
        return user_id;
    }

    public void setType(int type) {
        this.type = type;
    }
    public int getType() {
        return type;
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

    public void setAdd_time(long add_time) {
        this.add_time = add_time;
    }
    public long getAdd_time() {
        return add_time;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }
    public String getLocations() {
        return locations;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getContent() {
        return content;
    }

    public void setImage_list(List<String> image_list) {
        this.image_list = image_list;
    }
    public List<String> getImage_list() {
        return image_list;
    }

    public void setVideo(String video) {
        this.video = video;
    }
    public String getVideo() {
        return video;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }
    public String getVoice() {
        return voice;
    }

    public void setPage_view(int page_view) {
        this.page_view = page_view;
    }
    public int getPage_view() {
        return page_view;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }
    public int getComment_count() {
        return comment_count;
    }

    public void setThumbup_count(int thumbup_count) {
        this.thumbup_count = thumbup_count;
    }
    public int getThumbup_count() {
        return thumbup_count;
    }

    public void setIs_thumbup(int is_thumbup) {
        this.is_thumbup = is_thumbup;
    }
    public int getIs_thumbup() {
        return is_thumbup;
    }

    public void setIs_focus_on(int is_focus_on) {
        this.is_focus_on = is_focus_on;
    }
    public int getIs_focus_on() {
        return is_focus_on;
    }
}
