package com.example.myalarm;

import android.media.MediaPlayer;

public class MediaPlayerSingleton {
    private static MediaPlayer mediaPlayer;

    // Private constructor to prevent instantiation
    private MediaPlayerSingleton() {}

    // Get the instance of MediaPlayer
    public static MediaPlayer getMediaPlayer() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        return mediaPlayer;
    }

    // Set a MediaPlayer instance
    public static void setMediaPlayer(MediaPlayer player) {
        mediaPlayer = player;
    }

    // Release the MediaPlayer when no longer needed
    public static void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}

