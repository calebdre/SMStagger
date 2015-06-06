package com.caleblewis.textstagger;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class SMSScheduler {

    public void schedule(Context context, TextMessage message, Calendar calendar){
        Intent intent = new Intent(context, SendSMSReceiver.class);

        intent.putExtra("name", message.getName());
        intent.putExtra("number", message.getPhone());
        intent.putExtra("message", message.getMessage());

        PendingIntent sender = PendingIntent.getBroadcast(context, 192837, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager am = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
    }
}