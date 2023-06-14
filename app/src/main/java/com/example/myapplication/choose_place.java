package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class choose_place extends AppCompatActivity {
    public static int place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_place);
    }

    public void up(View view) {
        place += 5;
        Intent intent = new Intent(this, players_4_aerohockey.class);
        startActivity(intent);
    }

    public void down(View view) {
        place += 0;
        Intent intent = new Intent(this, players_4_aerohockey.class);
        startActivity(intent);
    }
}