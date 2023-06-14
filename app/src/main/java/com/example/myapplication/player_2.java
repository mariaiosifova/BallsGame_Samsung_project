package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class player_2 extends AppCompatActivity {
    SensorManager sensorManager;
    Sensor sensorAccel;
    StringBuilder sb = new StringBuilder();
    Timer timer;
    public static String place;
    public static float[] valuesAccelGravity = new float[2];
    public static String golf_hockey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(MainActivity5.player == 'l' || MainActivity5.player == 'r') {
            setContentView(new player_2_aerohockey(this));
            golf_hockey = "hockey";
        } else if(MainActivity5.player == 'u' || MainActivity5.player == 'd') {
            setContentView(new player_2_golf(this));
            golf_hockey = "golf";
        } else if(MainActivity5.player == 'H') {
            setContentView(new player_1_aerohockey(this));
            golf_hockey = "hockey";
        } else if(MainActivity5.player == 'V') {
            setContentView(new player_1_golf(this));
            golf_hockey = "golf";
        }
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorAccel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(listener, sensorAccel,
                SensorManager.SENSOR_DELAY_NORMAL);
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showInfo();
                    }
                });
            }
        };
        timer.schedule(task, 0, 400);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listener);

    }
    String format(float values[]) {
        return String.format("%1$.1f\t\t%2$.1f", values[0], values[1]);
    }

    void showInfo() {
        sb.setLength(0);
        sb.append("\nAccel gravity : " + format(valuesAccelGravity));
        //tvText.setText(sb);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
    SensorEventListener listener = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            switch (event.sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                    for (int i = 0; i < 2; i++) {
                        valuesAccelGravity[i] = (float) (0.1 * event.values[i] + 0.9 * valuesAccelGravity[i]);
                    }
                    break;
            }

        }

    };
}