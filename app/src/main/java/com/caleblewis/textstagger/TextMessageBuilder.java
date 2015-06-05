package com.caleblewis.textstagger;

import java.util.Date;

public class TextMessageBuilder {
    private String name, message, phone;
    private Date date;

    public TextMessage build() throws IncompleteTextMessageException {
        if(name != null && message != null && phone != null && date != null)
            return new TextMessage(name, message, phone, date);

        throw new IncompleteTextMessageException();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
