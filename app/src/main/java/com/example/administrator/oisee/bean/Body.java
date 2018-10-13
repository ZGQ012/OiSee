package com.example.administrator.oisee.bean;

public class Body {
    private String avatar; //头像
    private String nickname; //昵称
    private String sex; //性别
    private String personalized_signature; //个性签名
    private String text_introduce; //文字介绍
    private String voice_introduce;  //	语音介绍
    private String video_introduce;// 视频介绍
    private String nationality_id;//国籍id

    public Body() {
    }

    public Body(String avatar, String nickname, String sex, String personalized_signature, String text_introduce, String voice_introduce, String video_introduce, String nationality_id) {
        this.avatar = avatar;
        this.nickname = nickname;
        this.sex = sex;
        this.personalized_signature = personalized_signature;
        this.text_introduce = text_introduce;
        this.voice_introduce = voice_introduce;
        this.video_introduce = video_introduce;
        this.nationality_id = nationality_id;
    }


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPersonalized_signature() {
        return personalized_signature;
    }

    public void setPersonalized_signature(String personalized_signature) {
        this.personalized_signature = personalized_signature;
    }

    public String getText_introduce() {
        return text_introduce;
    }

    public void setText_introduce(String text_introduce) {
        this.text_introduce = text_introduce;
    }

    public String getVoice_introduce() {
        return voice_introduce;
    }

    public void setVoice_introduce(String voice_introduce) {
        this.voice_introduce = voice_introduce;
    }

    public String getVideo_introduce() {
        return video_introduce;
    }

    public void setVideo_introduce(String video_introduce) {
        this.video_introduce = video_introduce;
    }

    public String getNationality_id() {
        return nationality_id;
    }

    public void setNationality_id(String nationality_id) {
        this.nationality_id = nationality_id;
    }
}
