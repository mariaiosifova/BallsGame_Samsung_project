package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity5 extends AppCompatActivity {
    public static char player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
    }

    public void l(View view) {
        player = 'l';
        Intent intent = new Intent(this, player_2.class);
        startActivity(intent);
    }

    public void r(View view) {
        player = 'r';
        Intent intent = new Intent(this, player_2.class);
        startActivity(intent);
    }
}