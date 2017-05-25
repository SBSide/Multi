package com.ivsa.multi;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView t;
    int i = 0;
    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mhandler.post(new Runnable() {
                @Override
                public void run() {
                    t.setText("숫자: "+i);
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t = (TextView) findViewById(R.id.te);
    }

    class MyThread extends Thread{
        @Override
        public void run() {
            for (i = 1; i < 11; i++) {
                try {
                    Thread.sleep(1000);
                    Message msg = mhandler.obtainMessage();
                    mhandler.sendMessage(msg);
                    /*
                    mhandler.post(new Runnable() {
                        @Override
                        public void run() {
                            t.setText("숫자: "+i);
                        }
                    });*/
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void onClick(View v){
        MyThread thread = new MyThread();
        thread.setDaemon(true);
        thread.start();
    }
}
