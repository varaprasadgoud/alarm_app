package com.example.myalarm;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.io.IOException;
import java.util.Calendar;

public class AlarmService extends android.app.Service {

    private static final String CHANNEL_ID = "alarm_channel2";
    private static final int NOTIFICATION_ID = 2;
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Create a notification channel for Android O and above
        createNotificationChannel();

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);

        // Initialize the MediaPlayer and set it to the Singleton
        try{
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
            mediaPlayer.setDataSource(this, Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.forest_vibes));
            mediaPlayer.prepare();
            MediaPlayerSingleton.setMediaPlayer(mediaPlayer);
            mediaPlayer.setLooping(true);
        } catch (IOException e) {
            e.printStackTrace(); // Handle exception if the sound file cannot be loaded
        }

        // Build a notification for the foreground service
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Alarm Service Running")
                .setContentText("The service is running in the background.")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .build();

        // Start the service in the foreground
        startForeground(NOTIFICATION_ID, notification);

        // Schedule the alarm to go off at a specific time
        scheduleAlarm();

        return START_STICKY; // Keep the service running
    }

    // Create a notification channel for Android O and above
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Alarm Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    // Schedule the alarm
    private void scheduleAlarm() {
        // Get the time for the alarm (e.g., set for 10 seconds later as a test)
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 10);  // Schedule for 10 seconds from now

        // Create an intent to trigger the alarm
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_IMMUTABLE);

        // Get the AlarmManager system service
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Set the alarm to go off at the specified time
        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }

    // Function to set alarm volume independently
    private void setAlarmVolume(int volume) {
        // Set the volume for the alarm stream (0 to max volume)
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM);
        int volumeLevel = (int) (maxVolume * (volume / 100.0));  // Map to max volume scale
        audioManager.setStreamVolume(AudioManager.STREAM_ALARM, volumeLevel, 0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Clean up if needed
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

