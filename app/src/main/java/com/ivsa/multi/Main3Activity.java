package com.ivsa.multi;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Main3Activity extends AppCompatActivity {
    EditText name, rcv;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mThread.myHandler.getLooper().quit();
    }

    Handler maHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String naa = (String) msg.obj;
            rcv.setText(naa);
        }
    };

    mThread mThread = new mThread();
    class mThread extends Thread {
        myHandler myHandler = new myHandler();

        @Override
        public void run() {
            Looper.prepare();
            Looper.loop();
        }
    }

    class myHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            String na = (String) msg.obj;
            na = "Good job, "+ na;
            Message msg2 = Message.obtain();
            msg2.obj = na;
            maHandler.sendMessage(msg2);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        name = (EditText) findViewById(R.id.name);
        rcv  = (EditText) findViewById(R.id.thrcv);
        mThread.start();
    }

    public void onClick(View v){
        String na = name.getText().toString();
        Message msg = maHandler.obtainMessage();
        msg.obj = na;
        mThread.myHandler.sendMessage(msg);
    }
}
