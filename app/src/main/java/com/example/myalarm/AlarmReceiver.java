package com.example.myalarm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.io.IOException;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "alarm_channel";
    private static final int NOTIFICATION_ID = 0;

    @Override
    public void onReceive(Context context, Intent intent) {

        MediaPlayer mediaPlayer = MediaPlayerSingleton.getMediaPlayer();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            try {
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            mediaPlayer.start();
        }
        // Create a notification channel for Android O and above
        createNotificationChannel(context);

        // Create an intent to launch MainActivity when the notification is tapped
        Intent launchIntent = new Intent(context, AlarmActivity.class);
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(launchIntent);

//        PendingIntent pendingIntent = PendingIntent.getActivity(
//                context,
//                0,
//                launchIntent,
//                PendingIntent.FLAG_IMMUTABLE // Make it immutable since we don't modify the PendingIntent
//        );
//
//
//
//        // Create the notification
//        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, CHANNEL_ID)
//                .setContentTitle("ALARM!")
//                .setContentText("It's time!")
//                .setSmallIcon(android.R.drawable.ic_dialog_alert)
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setAutoCancel(true)
//                .setContentIntent(pendingIntent)
//                .setFullScreenIntent(pendingIntent, true);
//
//
//
//        // Get the NotificationManager to show the notification
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
//        // Create notification channel (required for Android O and above)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = "Alarm Channel";
//            String description = "Channel for alarm notifications";
//            int importance = NotificationManager.IMPORTANCE_HIGH;
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
//            channel.setDescription(description);
//            notificationManager.createNotificationChannel(channel);
//        }
//        if (notificationManager != null) {
//            notificationManager.notify(NOTIFICATION_ID, notification.build());
//        }
    }

    // Create the notification channel for Android O (API 26) and above
    private void createNotificationChannel(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "Alarm Channel";
            String description = "Channel for alarm notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            // Register the channel with the system
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
}