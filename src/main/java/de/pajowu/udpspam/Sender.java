package de.pajowu.udpspam;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import android.util.Log;
import android.os.Handler;

public class Sender implements Runnable {
    // ms = ms per packet
    long mspp;
    DatagramSocket s;
    Handler mHandler;
    DatagramPacket mDatagramPacket;
    Sender(long ms, DatagramSocket sock, Handler hnd, DatagramPacket pack)
    {
        mspp = ms;
        s = sock;
        mHandler = hnd;
        mDatagramPacket = pack;
    }
    public void run() {
        // TODO Auto-generated method stub
        Runnable snd = new Sender(mspp, s, mHandler, mDatagramPacket);
        mHandler.postDelayed(snd, mspp);
        try {
            s.send(mDatagramPacket);
        } catch (Exception e) {
            Log.d("UDPSPAM", "Exception", e);
        }
    }
}
