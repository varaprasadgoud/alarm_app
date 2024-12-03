package com.example.myalarm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.icu.util.Calendar;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

public class MainActivity extends AppCompatActivity {
    private MaterialTimePicker timePicker;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MediaPlayerSingleton.setMediaPlayer(MediaPlayer.create(this, R.raw.forest_vibes));

        TextView selectTimeView = findViewById(R.id.selectTime);
        selectTimeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePicker = new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(12)
                        .setMinute(0)
                        .setTitleText("Select Alarm Time")
                        .build();
                timePicker.show(getSupportFragmentManager(), "androidknowledge");
                timePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("Mainactivity",String.valueOf(timePicker));
                        if (timePicker.getHour() >= 12){
                            selectTimeView.setText(
                                    String.format("%02d",(timePicker.getHour() == 12 ? 12 : timePicker.getHour()-12)) +":"+ String.format("%02d", timePicker.getMinute())+"PM"
                            );
                        } else  {
                            selectTimeView.setText(timePicker.getHour()+":" + String.format("%02d", timePicker.getMinute())+ "AM");
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            calendar = Calendar.getInstance();
                            calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                            calendar.set(Calendar.MINUTE, timePicker.getMinute());
                            calendar.set(Calendar.SECOND, 0);
                            calendar.set(Calendar.MILLISECOND, 0);
                        }
                    }
                });
            }
        });

        // Start the AlarmService when a button is pressed
        Button startButton = findViewById(R.id.setAlarm);
        startButton.setOnClickListener(v -> {
            if(calendar != null){
                Intent serviceIntent = new Intent(MainActivity.this, AlarmService.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    serviceIntent.putExtra("hourOfDay", calendar.get(Calendar.HOUR_OF_DAY));
                    serviceIntent.putExtra("minute", calendar.get(Calendar.MINUTE));
                }
                startService(serviceIntent);
            }
        });

        Button cancelButton = findViewById(R.id.cancelAlarm);
        cancelButton.setOnClickListener(v -> {
            cancelAlarm();
        });
    }

    public void cancelAlarm() {
        // Create the same intent used to set the alarm
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);

        // Create the same PendingIntent used to set the alarm
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_IMMUTABLE);

        // Get the AlarmManager system service
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Cancel the alarm by passing the same PendingIntent
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
            MediaPlayerSingleton.releaseMediaPlayer();
        }
    }
}
