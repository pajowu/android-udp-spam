package de.pajowu.udpspam;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends Activity
{
	EditText etAddr;
	EditText etPort;
	SeekBar barSize;
	EditText etRate;
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
	            Thread t = new Thread(new Sender(ms, add, po, size));
	            t.start();
	        }
	    });
    }
}
