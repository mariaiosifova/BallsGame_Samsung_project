package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity6 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
    }

    public void u(View view) {
        MainActivity5.player = 'u';
        Intent intent = new Intent(this, player_2.class);
        startActivity(intent);
    }

    public void d(View view) {
        MainActivity5.player = 'd';
        Intent intent = new Intent(this, player_2.class);
        startActivity(intent);
    }
}