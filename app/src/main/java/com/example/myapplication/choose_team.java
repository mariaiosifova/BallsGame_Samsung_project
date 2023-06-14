package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class choose_team extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_team);
    }

    public void left_team(View view) {
        Intent intent = new Intent(this, choose_place.class);
        startActivity(intent);
        choose_place.place = 1;
    }

    public void right_team(View view) {
        Intent intent = new Intent(this, choose_place.class);
        startActivity(intent);
        choose_place.place = 2;
    }
}