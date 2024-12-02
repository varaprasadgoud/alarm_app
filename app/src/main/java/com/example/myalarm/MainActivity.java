package com.example.myalarm;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MediaPlayerSingleton.setMediaPlayer(MediaPlayer.create(this, R.raw.forest_vibes));

        // Start the AlarmService when a button is pressed
        Button startButton = findViewById(R.id.start_alarm_button);
        startButton.setOnClickListener(v -> {
//            if(MediaPlayerSingleton.getMediaPlayer().isPlaying()){
//                MediaPlayerSingleton.getMediaPlayer().stop();
//
//                try {
//                    MediaPlayerSingleton.getMediaPlayer().prepare();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }else{
//               MediaPlayerSingleton.getMediaPlayer().start();
//            }

            Intent serviceIntent = new Intent(MainActivity.this, AlarmService.class);
            startService(serviceIntent);  // Start the foreground service
        });
    }
}
