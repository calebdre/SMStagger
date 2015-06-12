package com.caleblewis.SMStagger.SMS;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.caleblewis.SMStagger.Models.TextMessage;

public class SMSScheduler {

    public void schedule(Context context, TextMessage message, long timeToKill){
        Intent intent = new Intent(context, SendSMSReceiver.class);

        intent.putExtra("name", message.getName());
        intent.putExtra("number", message.getPhone());
        intent.putExtra("message", message.getMessage());
        intent.putExtra("id", message.getId());

        PendingIntent sender = PendingIntent.getBroadcast(context, (int) message.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager am = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, timeToKill, sender);
    }

    public void cancel(Context context, TextMessage textMessage){
        Intent intent = new Intent(context, SendSMSReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, (int) textMessage.getId(), intent, 0);

        AlarmManager am = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        am.cancel(sender);
    }
}
