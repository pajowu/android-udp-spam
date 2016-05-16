package de.pajowu.udpspam;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.DatagramPacket;
import android.util.Log;
import java.lang.System;
import java.util.Random;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.content.Context;
import android.widget.Button;

public class Sender implements Runnable {
    // ms = ms per packet
    long mspp;
    int port = 5005;
    String addr = "10.0.0.7";
    Boolean allOK = true;
    Random rand;
    int dataSize;
    Context mContext;
    DatagramSocket s;
    InetAddress local;
    Button startBtn;
    Sender(long ms, String add, int po, int size, Context cont, Button btn)
    {
        rand = new Random();
        addr = add;
        port = po;
        mspp = ms;
        dataSize = size;
        mContext = cont;
        startBtn = btn;
        //run();
    }
    public void run() {
        // TODO Auto-generated method stub
        long start;
        long time;
        long sleep;
        byte[] message = new byte[dataSize];
        PowerManager powerManager = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
        WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Migration");
        wakeLock.acquire();
        setButton(false);
        try {
            s = new DatagramSocket();
            s.setReceiveBufferSize(10240);
            local = InetAddress.getByName(addr);
        } catch (Exception e) {
            Log.d("UDPSPAM", "Exception", e);
        }

        while (allOK && s != null && local != null) {
            start = System.currentTimeMillis();
            try {
                rand.nextBytes(message);
                DatagramPacket p = new DatagramPacket(message, dataSize, local,
                        port);
                s.send(p);
            } catch (Exception e) {
                Log.d("UDPSPAM", "Exception", e);
            }
            time = System.currentTimeMillis() - start;
            sleep = mspp - time;
            if (sleep > 0) {
                try {
                    Thread.sleep(sleep);
                } catch (Exception e) {
                    Log.d("UDPSPAM", "Exception", e);
                    allOK = false;
                }
            } else {
                Log.d("UDPSPAM", "HALP! Can't send enough packages");
                allOK = false;
            }
        }
        setButton(true);
        wakeLock.release();
    }
    private void setButton(final Boolean state) {
        ((MainActivity)mContext).runOnUiThread( new Runnable() {
            @Override
            public void run() {
                startBtn.setEnabled(state);
            }
        });
    }
}
