package com.googleplay.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.Date;

public class notedata extends AppCompatActivity {
    private String Idnote;
    String sub;
    Date datenote;
    String text;
    public notedata(String subject,String text,Date date){
        this.sub=subject;
        this.text=text;
        this.datenote=date;
    }
    public notedata(){}

    public String getText() { return text; }

    public String getSubject() {
        return sub;
    }

    public Date getDate() {
        return datenote;
    }

    public void setDate(Date date) {
        this.datenote = date;
    }

    public void setSubject(String subject) {
        this.sub = subject;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPublishId() {
        return Idnote;
    }

    public void setPublishId(String publishId) {
        this.Idnote = publishId;
    }
}

