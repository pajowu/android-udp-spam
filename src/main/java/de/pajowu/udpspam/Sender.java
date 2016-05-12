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

public class Sender implements Runnable {
    // ms = ms per packet
    long mspp;
    int port = 5005;
    String addr = "10.0.0.7";
    Boolean allOK = true;
    Random rand;
    int dataSize;
    Context mContext;
    Sender(long ms, String add, int po, int size, Context cont)
    {
        rand = new Random();
        addr = add;
        port = po;
        mspp = ms;
        dataSize = size;
        mContext = cont;
        run();
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
        while (allOK) {
            start = System.currentTimeMillis();
            try {

                String messageStr = "Hello Android!";
                DatagramSocket s = new DatagramSocket();
                s.setReceiveBufferSize(10240);
                InetAddress local = InetAddress.getByName(addr);
                rand.nextBytes(message);
                DatagramPacket p = new DatagramPacket(message, dataSize, local,
                        port);
                s.send(p);
            } catch (Exception e) {
                Log.d("UDPSPAM", "Exception", e);
                allOK = false;
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
        wakeLock.release();
    }
}
