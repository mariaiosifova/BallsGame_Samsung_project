package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class choose_location_1_player extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_location1_player);
    }

    public void to_hockey_1_player(View view) {
        MainActivity5.player = 'H';
        Intent intent = new Intent(this, player_2.class);
        startActivity(intent);
    }

    public void to_get_hole_1_player(View view) {
        MainActivity5.player = 'V';
        Intent intent = new Intent(this, player_2.class);
        startActivity(intent);
    }
}