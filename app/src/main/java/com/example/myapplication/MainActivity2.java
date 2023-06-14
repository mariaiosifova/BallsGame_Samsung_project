package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    TextView tv_server_addr;
    EditText et_server_addr;
    EditText et_count_devices;
    static TextView oops;
    public static NetworkServer srv;
    public static NetworkClient clnt;
    int counter = 0;
    public static int us;
    static MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tv_server_addr = findViewById(R.id.some_id);
        et_server_addr = findViewById(R.id.editTextText2);
        et_count_devices = findViewById(R.id.editTextText);

    }


    public void start_server(View view) {
        counter += 1;
        if (counter <= 1) {
            srv = new NetworkServer(getApplicationContext());
            tv_server_addr.setText("Код: " + srv.getLocalIP());
            srv.start();
        }
    }

//    public void start_client(View view) {
//        clnt = new NetworkClient(getApplicationContext());
//        clnt.start(et_server_addr.getText().toString());
//        Intent i = new Intent(MainActivity.this, choose_game.class);
//        startActivityForResult(i, 0);
//    }


    public void mode(View view) {
        clnt = new NetworkClient(getApplicationContext());
        clnt.start(et_server_addr.getText().toString());
        String users = et_count_devices.getText().toString();
        us = Integer.parseInt(users);
        if(us == 2) {
            Intent intent = new Intent(this, MainActivity4.class);
            startActivity(intent);
        } else if(us == 4) {
            Intent intent = new Intent(this, choose_team.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(this, choose_location_1_player.class);
            startActivity(intent);
        }
        if (MainActivity3.sound1==1) {
            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.back_music);
            mediaPlayer.start();
        }
    }
}