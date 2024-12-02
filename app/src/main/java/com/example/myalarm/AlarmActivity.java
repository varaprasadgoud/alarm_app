package com.example.myalarm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import java.io.IOException;

public class AlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        Button stopAlarmButton = findViewById(R.id.stop_alarm);
        stopAlarmButton.setOnClickListener(v -> {
            if (MediaPlayerSingleton.getMediaPlayer().isPlaying()) {
                MediaPlayerSingleton.getMediaPlayer().stop();
                try {
                    MediaPlayerSingleton.getMediaPlayer().prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}