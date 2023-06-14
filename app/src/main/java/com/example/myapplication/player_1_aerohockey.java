package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class player_1_aerohockey extends View {


    public player_1_aerohockey(Context context) {
        super(context);
    }
    Bitmap playing_background; //Задний фон игры
    Bitmap left_racket, right_racket; //Изображения ракеток
    Bitmap ball; //Мяч
    int racket_width = 30, racket_height = 150; //Ширина и высота ракеток
    double kx,ky; //Переменные-приспособители ширины и высоты экрана
    volatile int Tx = -1,Ty = -1; //Определение координат нажатия
    volatile int ry1 = 0;//начальная координата первой полоски по у(верхняя точка)
    volatile int ryy1 = ry1 + 200; //начальная координата первой полоски по у(нижняя точка)
    volatile int ry2 = 0;//начальная координата второй полоски по у(верхняя точка)
    volatile int ryy2 = ry2 + 200; //начальная координата второй полоски по у(нижняя точка)
    NetworkClient clnt=MainActivity2.clnt;
    double x, y;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w==0 || h==0) return;
        MainLoop m = new MainLoop();
        m.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        kx=getWidth()/800.;
        ky=getHeight()/1000.;
        //создание левой ракетки
        left_racket = BitmapFactory.decodeResource(getResources(), R.drawable.racket);
        left_racket = Bitmap.createScaledBitmap(left_racket,(int)(racket_width * kx), (int)(racket_height * ky), false);
        //создание правой ракетки
        right_racket = BitmapFactory.decodeResource(getResources(), R.drawable.racket);
        right_racket = Bitmap.createScaledBitmap(right_racket,(int)(racket_width * kx), (int)(racket_height * ky), false);
            playing_background = BitmapFactory.decodeResource(getResources(), R.drawable.play_back);
            playing_background = Bitmap.createScaledBitmap(playing_background, getWidth(), getHeight(), false);

        //создание рисунка мячика
        ball = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
        ball = Bitmap.createScaledBitmap(ball, 150, 150,false);



    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint();
        //рисовка заднего фона
        canvas.drawBitmap(playing_background, 0, 0, p);
        //рисовка левой ракетки
        canvas.drawBitmap(left_racket, 0, (int)(ry1*ky), p);
        //рисовка правой ракетки
        canvas.drawBitmap(right_racket, (int) (getWidth() - (int)(racket_width*kx)), (int)(ry2*ky), p);

        canvas.drawBitmap(ball, (int)(clnt.c_x*kx), (int)(clnt.c_y*ky), p);


    }



    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        //принятие координат нажатия на экран
        Tx = (int) event.getX();
        Ty = (int) event.getY();
        if(Tx > getWidth() / 2) {// Двигаем ракетки | Правая
            if (Ty > ryy2) {
                clnt.sendCoords(-1, -1, -1, ry2+40);
            }
            else  if (Ty < ry2){
                clnt.sendCoords(-1, -1, -1, ry2-40);
            }
        } else {  //  Двигаем ракетки | Левая.
            //MainActivity2.gamer='L';
            if (Ty > ryy1) {
                clnt.sendCoords(-1, ry1+40, -1, -1);
            }
            else if (Ty < ry1){
                clnt.sendCoords(-1, ry1-40, -1, -1);
            }
        }
        return true;
    }
    class MainLoop extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            if (clnt.c_x > getWidth() || clnt.c_x < 0 || clnt.c_y > getHeight() || clnt.c_y < 0) {
                player_2_aerohockey.ishere = false;
            } else {
                player_2_aerohockey.ishere = true;
            }
            boolean b = true;
            while (b) {
                clnt.getCoords();
                ry1=(int)(clnt.l_y); ryy1=(int)(clnt.l_y+200);
                ry2=(int)(clnt.r_y); ryy2=(int)(clnt.r_y+200);
                x=clnt.c_x*kx; y=clnt.c_y*ky;
                if (clnt.c_x >= 750 - 30) {
                    if(clnt.c_y >= ry2-75 && clnt.c_y <= ryy2-150/2) {
                        player_2_aerohockey.here = 'T';
                    }
                    else {
                        player_2_aerohockey.here = 'F';
                    }
                } else if (clnt.c_x <50) {
                    if (clnt.c_y >= ry1-75 && clnt.c_y <=ryy1 - 150/2) {
                        player_2_aerohockey.here = 'T';
                    }
                    else {
                        player_2_aerohockey.here = 'F';
                    }
                }
                else {
                    player_2_aerohockey.here = 'T';
                }
                invalidate();
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}