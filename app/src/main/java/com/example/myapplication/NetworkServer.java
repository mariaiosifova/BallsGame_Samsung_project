package com.example.myapplication;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.text.format.Formatter;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.LinkedList;

public class NetworkServer {
    Context context;
    private String localIP;
    int l_x,l_y,r_x,r_y, c_r;
    int c_x = 200;
    int c_y = 200;
    Server server;
    MediaPlayer mp1;
    //NetworkClient clnt=MainActivity.clnt;
    public static LinkedList<String> list_ip = new LinkedList<>();
    public int vx = 3;//скорость шарика по х
    public int vy = 3;//скорость шарика по у
    public NetworkServer(Context ctx){
        context=ctx;}
    public String getLocalIP(){
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        localIP= Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        return localIP;
    }

    public void start(){
        server = new Server();
        Kryo kryo = server.getKryo();
        kryo.register(Message.class);
        server.start();
        try {
            server.bind(54555, 54777);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        server.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof Message) {
                    Message request = (Message) object;
                    switch (request.command){
                        case "get":
                            Message response = new Message();
                            response.command = "coord";
                            response.l_x=l_x;
                            response.l_y=l_y;
                            response.r_x=r_x;
                            response.r_y=r_y;
                            response.c_r = c_r;
                            response.c_y = c_y;
                            response.c_x = c_x;
                            connection.sendTCP(response);
                            break;
                        case "send":
                            l_x=request.l_x>=0?request.l_x:l_x;
                            l_y=request.l_y>=0?request.l_y:l_y;
                            r_x=request.r_x>=0?request.r_x:r_x;
                            r_y=request.r_y>=0?request.r_y:r_y;
                            break;
                    }
                }
            }
            public void connected(Connection con){
                System.out.println("Hey, we are connected!");
            }
            public void disconnected(Connection con){
                System.out.println(":( He is gone... he'll never come back..");
            }
        });
        class MainLoop extends AsyncTask {
            @Override
            protected Object doInBackground(Object[] objects) {
                boolean b = true;
                while (b) {
                    if(MainActivity3.sound.isChecked()) {
                        if (!MainActivity.mediaPlayer.isPlaying()) {
                            MainActivity.mediaPlayer.start();
                        }
                    }
                    c_x += vx;
                    c_y += vy;
                    if (MainActivity5.player == 'u' || MainActivity5.player == 'd' || MainActivity5.player == 'V') {
                        c_x += vx + -((player_2.valuesAccelGravity[0]) * 5);
                        c_y += vy + (player_2.valuesAccelGravity[1]);
                    }
                    if (MainActivity3.rotate.isChecked()) {
                        c_x += vx + -((player_2.valuesAccelGravity[0]) * 3);
                        c_y += vy + (player_2.valuesAccelGravity[1]);
                    }
                    if (player_2_aerohockey.here=='F') {
                        c_x = 200;
                        c_y = 200;
                        vy = -vy;
                        vx = -vx;
                    }
                        if (c_x >= 800 - 60 || c_x <= 0) {
                            if (c_x >= 800 - 60) {
                                c_x = 800 - 65;
                                vx = -vx;
                            }
                            if (c_x <= 0) {
                                c_x = 5;
                                vx = -vx;
                            }
                        }
                    if (c_y >= 1000 - 60  || c_y <= 0) {
                        if(c_y >= 1000-60) {
                            c_y = 1000-65;
                            vy = -vy;
                        }
                        if(c_y <=0) {
                            c_y = 5;
                            vy = -vy;
                        }
                    }
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        }
        MainLoop m = new MainLoop();
        m.execute();
    }
}