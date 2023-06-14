package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.view.View;

public class player_1_golf extends View {
    public player_1_golf(Context context) {
        super(context);
    }
    int mills_counter = 0;
    Bitmap playing_background; //Задний фон игры
    Bitmap left_racket, right_racket; //Изображения ракеток
    Bitmap ball; //Мяч
    Bitmap black_hole;
    //Bitmap dog;
    //public static boolean ishere;
    int f_n = 0;
    int seconds;
    //double counter_dog = 0;
    int racket_width = 30, racket_height = 150; //Ширина и высота ракеток
    //    public volatile static char gamer='L'; //Переменная-определитель стороны устройства
    double kx,ky; //Переменные-приспособители ширины и высоты экрана
    static volatile int Tx = -1,Ty = -1; //Определение координат нажатия
    volatile int ry1 = 0;//начальная координата первой полоски по у(верхняя точка)
    volatile int ryy1 = ry1 + 200; //начальная координата первой полоски по у(нижняя точка)
    volatile int ry2 = 0;//начальная координата второй полоски по у(верхняя точка)
    volatile int ryy2 = ry2 + 200; //начальная координата второй полоски по у(нижняя точка)
    NetworkClient clnt=MainActivity2.clnt;
    //    public volatile static char here = 'T';
    int counter_cool = 0;
    double x, y;
    int bh_x = (int) (Math.random() * 1000);
    int bh_y = (int) (Math.random() * 1000);

    public static boolean touch = false;
    int intent;
    int seconds_counter=60;
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w==0 || h==0) return;
        MainLoop m = new MainLoop();
        m.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//        mp1 = MediaPlayer.create(this, R.raw.back_music);
//        mp1.start();
        kx=getWidth()/800.;
        ky=getHeight()/1000.;
//        ky*=2;
//        dog = BitmapFactory.decodeResource(getResources(), R.drawable.dog);
//        dog = Bitmap.createScaledBitmap(dog,(int)(300*kx), (int)(300*ky), false);
        //MainActivity2.mp2 = MediaPlayer.create(getContext(), R.raw.utka);
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


        black_hole = BitmapFactory.decodeResource(getResources(), R.drawable.black_hole);
        black_hole = Bitmap.createScaledBitmap(black_hole, 300, 300,false);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint();
        //рисовка заднего фона
        canvas.drawBitmap(playing_background, 0, 0, p);
        p.setTextSize(75);
        p.setStyle(Paint.Style.FILL);
        p.setTypeface(Typeface.create("Arial", Typeface.ITALIC));
        canvas.drawText("Время "+seconds_counter,20, 55, p);
        canvas.drawText("Очки "+player_2_golf.black_hole_counter, 20, 110, p);
//        if (choose_mode_2_players.v_or_g == 'V') {
            canvas.drawBitmap(black_hole, bh_x, bh_y, p);
            if (clnt.c_x * kx >= bh_x && clnt.c_x * kx <= bh_x + 300 && (int)(clnt.c_y*(ky)) >= bh_y && (int)(clnt.c_y*(ky)) <= bh_y + 300) {
                bh_x =(int) (Math.random() * 1000);
                bh_y = (int) (Math.random() * 1000);
                player_2_golf.black_hole_counter += 1;
            }
       // }
        canvas.drawBitmap(ball, (int) (clnt.c_x * kx), (int) (clnt.c_y * ky), p);

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
                mills_counter += 25;
                if(mills_counter == 1000) {
                    mills_counter = 0;
                    seconds_counter-=1;
                }
                seconds = (int)(Math.random() * 10);
                clnt.getCoords();
                x=clnt.c_x*kx; y=clnt.c_y*ky;
                player_2_aerohockey.here = 'T';
                invalidate();
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (seconds_counter == 0) {
                    return null;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Intent i = new Intent(player_1_golf.this.getContext(), golf_result.class);
            player_1_golf.this.getContext().startActivity(i);
        }
    }
}
