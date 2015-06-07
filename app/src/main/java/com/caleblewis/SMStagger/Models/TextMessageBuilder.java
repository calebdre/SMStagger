package com.caleblewis.SMStagger.Models;

import com.caleblewis.SMStagger.Exceptions.IncompleteTextMessageException;

public class TextMessageBuilder {
    private String name;
    private String message;
    private String phone;
    private String date;
    private long id;

    public TextMessage build() throws IncompleteTextMessageException {
        if(name != null && message != null && phone != null && date != null)
            return new TextMessage(name, message, phone, date, id);

        throw new IncompleteTextMessageException();
    }

    public void setId(long id) {this.id = id;}

    public void setName(String name) {
        this.name = name;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
