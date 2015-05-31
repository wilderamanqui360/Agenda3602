package com.example.empresa.agenda360.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.example.empresa.agenda360.R;
import com.example.empresa.agenda360.receiver.GcmBroadcastReceiver;
import com.google.android.gms.gcm.GoogleCloudMessaging;


import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by REAPRO on 19/04/2015.
 */
public class GcmIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public GcmIntentService(String name) {
        super(name);
    }

    public GcmIntentService() {
        super("");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras= intent.getExtras();
        GoogleCloudMessaging gcm=GoogleCloudMessaging.getInstance(this);
        String  messageType= gcm.getMessageType(intent);
        if(extras!=null && !extras.isEmpty()){
            if(GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)){
                Logger.getLogger("GCM_RECEIVED").log(Level.INFO, extras.toString());




                showToast(extras.getString("ID_NOTI")+"/"+extras.getString("ID_NOTI_ALUMNO")+"/"+extras.getString("ID_NOTI_DESCRIPCION"));
            }

        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    protected void showToast(final String message) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                NotificationManager notificationManager= (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                Notification notification=new Notification.Builder(getApplicationContext())
                        .setSmallIcon(R.drawable.ic_action_email)
                        .setContentTitle("AgendaMovil")
                        .setContentInfo(""+message).build();
                notificationManager.notify(0,notification);



            }
        });
    }
}
