package com.example.myalarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class AlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        String saved_quote = sh.getString("quote",null);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button stopAlarmButton = findViewById(R.id.stop_alarm);
        TextView quote = findViewById(R.id.quote);
        EditText typedQuote = findViewById(R.id.typedQuote);
        Log.d("TAG", saved_quote);
        if( saved_quote != null){
            quote.setText(saved_quote);
        }

        stopAlarmButton.setOnClickListener(v -> {
            String textFromTextView = quote.getText().toString();
            String textFromEditText = typedQuote.getText().toString();

            if (textFromTextView.equals(textFromEditText)) {
                Toast.makeText(AlarmActivity.this, "Good Morning! Have a nice day.", Toast.LENGTH_SHORT).show();
                if (MediaPlayerSingleton.getMediaPlayer().isPlaying()) {
                    MediaPlayerSingleton.getMediaPlayer().stop();
                    try {
                        MediaPlayerSingleton.getMediaPlayer().prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(AlarmActivity.this, "Wake up! Type your motivation properly!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Prevent back button while the alarm is active
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            // Hide system UI to ensure full-screen mode
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                    View.SYSTEM_UI_FLAG_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
}