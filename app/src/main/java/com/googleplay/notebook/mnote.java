package com.googleplay.notebook;

public class mnote {
    private String Id;
    private String topic;
    private String de;
    private String date;
    private String user;

    public mnote(){}

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getTitle() {
        return topic;
    }

    public void setTitle(String title) {
        this.topic = title;
    }

    public String getDesc() {
        return de;
    }

    public void setDesc(String desc) {
        this.de = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

}
