package com.caleblewis.textstagger;

/**
 * Created by caleblewis1 on 6/6/15.
 */
public class SMSIDNotFoundException extends Throwable {

    public SMSIDNotFoundException() {
        super("Could not find the ID of the SMS");
    }
}
