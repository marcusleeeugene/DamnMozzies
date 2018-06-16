package com.example.marcus.damnmozzies;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout mainactivity;
    TextView tbstatus;
    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainactivity = (ConstraintLayout) findViewById(R.id.mainactivitylayout);
        tbstatus = (TextView) findViewById(R.id.tbstatus);

        mainactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tbstatus.getText().equals("OFF")){ //Turn ON
                    tbstatus.setText("ON");
                    mainactivity.setBackgroundColor(Color.GREEN);
                    thread.start();
                }
                else if(tbstatus.getText().equals("ON")){ //Turn OFF
                    tbstatus.setText("OFF");
                    mainactivity.setBackgroundColor(Color.RED);
                    mHandler.removeCallbacks(thread);
                }


            }
        });
    }

    Thread thread = new Thread() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    frequencyemission emitfreq = new frequencyemission();
                    emitfreq.genTone();
                    if(tbstatus.getText().equals("ON")){
                        emitfreq.playSound("ON");
                    }
                    else if(tbstatus.getText().equals("OFF")){
                        emitfreq.playSound("OFF");
                    }
                    mHandler.postDelayed(thread, 10000);
                }
            });
        }
    };
}
