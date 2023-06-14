package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;
import java.net.InetAddress;

public class NetworkClient {

    Context context;

    Client client;

    String srvIP;
    private String localIP;

    int l_x, l_y, r_x, r_y, c_r, c_x, c_y;
    static int counter_client = 0;

    public NetworkClient(Context ctx) {
        context = ctx;
        client = new Client();
        Kryo kryo = client.getKryo();
        kryo.register(Message.class);
        client.addListener(new Listener() {

            public void received(Connection connection, Object object) {
                if (object instanceof Message) {
                    Message response = (Message) object;
                    l_x = response.l_x;
                    l_y = response.l_y;
                    r_x = response.r_x;
                    r_y = response.r_y;
                    c_r = response.c_r;
                    c_x = response.c_x;
                    c_y = response.c_y;
                }
            }

        });
        client.start();
    }

    public void start(String addr) {
        class ClientThread extends Thread {
            @Override
            public void run() {
                super.run();
                srvIP = addr;
                try {
                    client.connect(5000, srvIP, 54555, 54777);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        new ClientThread().start();
    }

    public void findServer() {
        new Thread() {

            @Override

            public void run() {
                srvIP = null;
                InetAddress address = client.discoverHost(54777, 5000);
                if (address != null)
                    srvIP = address.getHostAddress();
            }

        }.start();
    }

    public void sendCoords(int l_x, int l_y, int r_x, int r_y) {
        class ClientThread extends Thread {
            @Override
            synchronized public void run() {
                Message request = new Message();
                request.command = "send";
                request.l_x = l_x;
                request.l_y = l_y;
                request.r_x = r_x;
                request.r_y = r_y;
                client.sendTCP(request);
            }
        }
        ClientThread t = new ClientThread();
        t.start();
    }

    public void getCoords() {
        class SrvThread extends Thread {
            @SuppressLint("SuspiciousIndentation")
            @Override
            synchronized public void run() {
                try {
                    if(!client.isConnected())
                        client.connect(5000, srvIP, 54555, 54777);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Message request = new Message();
                request.command = "get";
                client.sendTCP(request);
            }
        }
        SrvThread t = new SrvThread();
        t.start();
    }
}