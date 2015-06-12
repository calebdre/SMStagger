package com.caleblewis.SMStagger.SMS;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

import com.caleblewis.SMStagger.MessagesDB;
import com.caleblewis.SMStagger.R;

public class SendSMSReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent){
        String name = intent.getStringExtra("name");
        String number = intent.getStringExtra("number");
        String message = intent.getStringExtra("message");
        long id = intent.getLongExtra("id", -1);


        if(id == -1){
            Notification noti = new Notification.Builder(context)
                    .setContentTitle("Scheduled Text Message Sent")
                    .setContentText("Could not send scheduled SMS")
                    .setSmallIcon(R.drawable.ic_action_mail)
                    .build();

            NotificationManager nm = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            nm.notify(1, noti);
            return;
        }

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(number, null, message, null, null);

        Notification noti = new Notification.Builder(context)
                .setContentTitle("SMS Sent")
                .setContentText("Scheduled message for " + name.replaceAll("\\s+","") + " was sent.")
                .setSmallIcon(R.drawable.ic_action_mail)
                .build();

        NotificationManager nm = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        nm.notify(1, noti);

        MessagesDB db = new MessagesDB(context);
        db.markMessageSent(Long.toString(id));
    }
}
