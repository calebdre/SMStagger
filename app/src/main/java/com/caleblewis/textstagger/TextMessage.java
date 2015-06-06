package com.caleblewis.textstagger;

import java.util.Date;

public class TextMessage {
    private String name, message, phone, date;
    private long id;
    private int sent = 0;

    public int getSent() {
        return sent;
    }

    public void setSent(int sent) {
        this.sent = sent;
    }

    public TextMessage(String name, String message, String phone, String date) {
        this.name = name;
        this.message = message;
        this.phone = phone;
        this.date = date;
    }

    public TextMessage(String name, String message, String phone, String date, long id) {
        this.name = name;
        this.message = message;
        this.phone = phone;
        this.date = date;
        this.id = id;
    }

    public long getId() {return id;}

    public void setId(long id) {this.id = id; }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public String getPhone() {
        return phone;
    }

    public String getDate() {
        return date;
    }
}
