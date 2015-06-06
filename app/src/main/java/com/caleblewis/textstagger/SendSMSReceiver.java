package com.caleblewis.textstagger;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

public class SendSMSReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        String name = intent.getStringExtra("name");
        String number = intent.getStringExtra("number");
        String message = intent.getStringExtra("message");
        String id = intent.getStringExtra("id");

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(number, null, message, null, null);

        Notification noti = new Notification.Builder(context)
                .setContentTitle("Scheduled Text Message Sent")
                .setContentText("The message you scheduled for " + name + " has been sent.")
                .setSmallIcon(R.drawable.ic_action_drop)
                .build();

        NotificationManager nm = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        nm.notify(1, noti);

        MessagesDB db = new MessagesDB(context);
        db.markMessageSent(id);
    }
}
