package com.caleblewis.textstagger;

import java.util.Date;

public class TextMessage {
    private String name, message, phone;
    private Date date;

    public TextMessage(String name, String message, String phone, Date date) {
        this.name = name;
        this.message = message;
        this.phone = phone;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public String getPhone() {
        return phone;
    }

    public Date getDate() {
        return date;
    }
}
