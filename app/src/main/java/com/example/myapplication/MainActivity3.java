package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

public class MainActivity3 extends AppCompatActivity {
    public static CheckBox rotate;
    public static CheckBox sound;
    public static int rotate1;
    public static int sound1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        rotate = findViewById(R.id.checkBox);
        sound = findViewById(R.id.checkBox2);
        if (sound.isChecked()) {
            sound1 = 1;
        }
        if (rotate.isChecked()) {
            rotate1 = 1;
        }

    }

    public void to_game(View view) {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
}