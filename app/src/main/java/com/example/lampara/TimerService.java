package com.example.lampara;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class TimerService extends Service {
    public static final String ACTION_UPDATE_TIMER = "com.example.lampara.UPDATE_TIMER";
    public static final String EXTRA_TIME_REMAINING = "com.example.lampara.TIME_REMAINING";
    private static final String CHANNEL_ID = "TimerChannel";
    private static final int NOTIFICATION_ID = 1;

    private Handler handler = new Handler();
    private long endTime;
    private NotificationManager notificationManager;

    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            long currentTime = SystemClock.elapsedRealtime();
            long timeRemaining = endTime - currentTime;

            if (timeRemaining > 0) {
                Intent intent = new Intent(ACTION_UPDATE_TIMER);
                intent.putExtra(EXTRA_TIME_REMAINING, timeRemaining);
                sendBroadcast(intent);
                updateNotification(timeRemaining);
                handler.postDelayed(this, 1000); // Repite cada segundo
            } else {
                onTimerFinish();
                stopSelf();

            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int hours = intent.getIntExtra("hours", 0);
        int minutes = intent.getIntExtra("minutes", 0);
        int seconds = intent.getIntExtra("seconds", 0);

        long duration = (hours * 3600 + minutes * 60 + seconds) * 1000;
        endTime = SystemClock.elapsedRealtime() + duration;

        handler.post(timerRunnable);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(timerRunnable);
        notificationManager.cancel(NOTIFICATION_ID);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Timer Channel",
                    NotificationManager.IMPORTANCE_LOW
            );
            notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void updateNotification(long timeRemaining) {
        int hours = (int) (timeRemaining / 1000) / 3600;
        int minutes = (int) ((timeRemaining / 1000) % 3600) / 60;
        int seconds = (int) (timeRemaining / 1000) % 60;

        String timeLeftFormatted = String.format("%02d:%02d:%02d", hours, minutes, seconds);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Temporizador en Ejecución")
                .setContentText("Tiempo restante: " + timeLeftFormatted)
                .setSmallIcon(R.drawable.toggle_on)
                .setOngoing(true)
                .build();

        startForeground(NOTIFICATION_ID, notification);
    }



    // Nueva función que se ejecuta cuando el temporizador llega a cero
    private void onTimerFinish() {
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Temporizador Finalizado")
                .setContentText("El tiempo ha llegado a su fin.")
                .setSmallIcon(R.drawable.toggle_on)
                .build();
        notificationManager.notify(NOTIFICATION_ID + 1, notification);

        Intent intent = new Intent("com.example.lampara.TIMER_FINISHED");
        sendBroadcast(intent);
    }
}
