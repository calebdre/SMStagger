package com.caleblewis.textstagger;

public class IncompleteTextMessageException extends Throwable {
    public IncompleteTextMessageException(){
        super("Not all of the fields for a text message have been set");
    }
}
