package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class no_internet_aerohockey extends View {


    public no_internet_aerohockey(Context context) {
        super(context);
    }
    Bitmap playing_background; //Задний фон игры
    Bitmap left_racket, right_racket; //Изображения ракеток
    Bitmap ball; //Мяч
    volatile int x = 300;//начальная координата шарика по х
    volatile int y = 300;//начальная координата шарика по у
    volatile int ry1 = 0;//начальная координата первой полоски по у
    volatile int ryy1 = ry1 + 200;
    volatile int ry2 = 0;//начальная координата второй полоски по у
    volatile int ryy2 = ry2 + 200;
    volatile int vx = 15 + (int) (Math.random() * 10);//скорость шарика по х
    volatile int vy = 15 + (int) (Math.random() * 10);//скорость шарика по у
    int r = 50;//радиус кружочка
    volatile int Tx = -1,Ty = -1;//создание переменной для определения куда нажал пользователь по х,y
    static int counter = 0;
    static int count_result1 = 0;
    static int count_result2 = 0;
    Paint fontPaint;//создание объекта класса пэинт
    MediaPlayer mp;
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w==0 || h==0) return;
        //создание левой ракетки
        left_racket = BitmapFactory.decodeResource(getResources(), R.drawable.racket);
        left_racket = Bitmap.createScaledBitmap(left_racket,100, 300, false);
        //создание правой ракетки
        right_racket = BitmapFactory.decodeResource(getResources(), R.drawable.racket);
        right_racket = Bitmap.createScaledBitmap(right_racket,100,300, false);
        playing_background = BitmapFactory.decodeResource(getResources(), R.drawable.play_back);
        playing_background = Bitmap.createScaledBitmap(playing_background, getWidth(), getHeight(), false);
        //создание рисунка мячика
        ball = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
        ball = Bitmap.createScaledBitmap(ball, 150, 150,false);
        MainLoop m = new MainLoop();
        m.execute();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        Tx = (int) event.getX();
        Ty = (int) event.getY();
        if(Tx > getWidth() / 2) {// Двигаем ракетки | Правая
            if (Ty < ry2) {
                ry2 -= 20;
                ryy2 -= 20;
            }
            else  if (Ty > ryy2){
                ry2 += 20;
                ryy2 += 20;
            }
        } else {  //  Двигаем ракетки | Левая.
            //MainActivity2.gamer='L';
            if (Ty < ry1) {
                ry1 -= 20;
                ryy1 -= 20;
            }
            else if (Ty > ryy1){
                ry1 += 20;
                ryy1 += 20;
            }
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint();
        //рисовка заднего фона
        canvas.drawBitmap(playing_background, 0, 0, p);
        //рисовка левой ракетки
        canvas.drawBitmap(left_racket, 0,ry1, p);
        //рисовка правой ракетки
        canvas.drawBitmap(right_racket, getWidth() - 75, ry2, p);

        canvas.drawBitmap(ball, (int)(x), (int)(y), p);
    }

    class MainLoop extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            boolean b = true;
            while (b) {
                if (x >= getWidth() - 150) {
                    if(y >= ry2 && y <= ryy2) {
                        vx = -vx;
                        count_result2 += 1;
                    }
                    else {
                        x = 300;
                        y = 300;
                        vy = -vy;
                        count_result2 = 0;
                    }
                } else if (x <= 75) {
                    if (y >= ry1 && y<=ryy1) {
                        vx = -vx;
                        count_result1 += 1;
                    }
                    else {
                        x = 300;
                        y = 300;
                        vx = -vx;
                        count_result1 = 0;
                    }
                }
                if (x < 0 || x > getWidth()) {
                    x = 300;
                    y = 300;
                }
                if (y <= 0 || y >= getHeight() - 75) {
                    vy = -vy;
                }
                x += vx;
                y += vy;
                invalidate();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}