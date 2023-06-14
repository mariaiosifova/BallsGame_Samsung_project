package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayer = MediaPlayer.create(this, R.raw.back_music);
    }

    public void play(View view) {
        Intent intent = new Intent(this, MainActivity3.class);
        startActivity(intent);

    }

    public void no_connect_play(View view) {
        Intent intent = new Intent(this, no_internet_activity.class);
        startActivity(intent);
    }
}
//