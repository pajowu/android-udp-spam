package de.pajowu.udpspam;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;
import android.content.Context;
import android.widget.CheckBox;
import android.view.WindowManager;
import android.util.Log;
import android.view.Window;

public class MainActivity extends Activity
{
	EditText etAddr;
	EditText etPort;
	SeekBar barSize;
	EditText etRate;
	CheckBox cbScreen;
	Window mWindow;
	Context cont;
	int size = 100;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
	    Button b = (Button) findViewById(R.id.start_button);
	    etAddr = (EditText) findViewById(R.id.ip_text);
	    etPort = (EditText) findViewById(R.id.port_text);
	    barSize = (SeekBar) findViewById(R.id.size_bar);
	    etRate = (EditText) findViewById(R.id.rate_text);
	    cbScreen = (CheckBox) findViewById(R.id.check_screen);
	    cont = this;
	    mWindow = getWindow();
	    barSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
				size = progress;
			}
			public void onStartTrackingTouch(SeekBar seekBar) {}

			public void onStopTrackingTouch(SeekBar seekBar) {
				Toast.makeText(MainActivity.this,"packet size: "+size+". Values > 1024 do not work reliable on my phone!", 
						Toast.LENGTH_SHORT).show();
			}
		});
	    b.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	        	String add = etAddr.getText().toString();
				int po = Integer.parseInt(etPort.getText().toString());
				int rate = Integer.parseInt(etRate.getText().toString());
				long ms = 1000/rate;
				Boolean kso = cbScreen.isChecked();
				Log.d("UDPSP_MAIN", kso.toString());
				if (kso) {
					mWindow.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
				}
	            Thread t = new Thread(new Sender(ms, add, po, size, cont));
	            t.start();
	        }
	    });
    }
}
