package com.tiagofarinha.inmezzoapp.Services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import com.tiagofarinha.inmezzoapp.Cache.BackgroundResourceLoader;
import com.tiagofarinha.inmezzoapp.R;

public class NotificationService extends Service {

    private final static String CHANNEL_ID = "1", CHANNEL_NAME = "InMezzoNots", CHANNEL_DESCR = "InMezzo Notificações";
    private BackgroundResourceLoader rl;
    private int not_id;

    public void sendNotification(String msg) {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        mBuilder.setContentTitle("InMezzo Notificação");
        mBuilder.setContentText(msg);
        mBuilder.setSmallIcon(R.drawable.emoji_happy);
        mBuilder.setAutoCancel(true);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
            mBuilder.setChannelId(CHANNEL_ID);

            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel);
        }

        assert mNotificationManager != null;
        mNotificationManager.notify(not_id++, mBuilder.build());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        rl = new BackgroundResourceLoader(this);
        rl.execute();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        rl = null;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}