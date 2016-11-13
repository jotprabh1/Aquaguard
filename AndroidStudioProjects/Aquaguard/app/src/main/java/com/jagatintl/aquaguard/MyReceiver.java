package com.jagatintl.aquaguard;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;

/*
 * Created by Prabhjot Singh on 13-11-2016.
 */
public class MyReceiver extends BroadcastReceiver{
    SharedPreferences preferences;

    @Override
    public void onReceive(Context context, Intent intent) {
        preferences=context.getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);
        int pos=preferences.getInt("Position",-1);
        if(pos>=0)
        {
            Intent intent1=new Intent(context,SplashScreen.class);
            PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent1,PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder=new NotificationCompat.Builder(context);
            builder.setSmallIcon(R.mipmap.icon);
            builder.setContentTitle("Like your new Aquaguard?");
            builder.setContentText("Please post a review for the new Aquaguard that you purchased...");
            builder.setContentIntent(pendingIntent);
            NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0,builder.build());

        }
    }
}
