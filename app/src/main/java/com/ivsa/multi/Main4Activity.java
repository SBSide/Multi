package com.ivsa.multi;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Main4Activity extends AppCompatActivity {
    TextView progress;
    ProgressBar pbar;
    myTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        progress = (TextView) findViewById(R.id.prog);
        pbar = (ProgressBar) findViewById(R.id.progbar);
    }

    class myTask extends AsyncTask<Integer,Integer,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pbar.setProgress(0);
            progress.setTextColor(Color.RED);
            progress.setText("진행률: 0%");
        }

        @Override
        protected Void doInBackground(Integer... params) {
            for(int i = 1; i < 101; i++){
                if (isCancelled()) return null;
                try {
                    Thread.sleep(200);
                    publishProgress(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            pbar.setProgress(values[0]);
            progress.setText("진행률: "+ values[0]+"%");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pbar.setProgress(100);
            progress.setTextColor(Color.BLACK);
            progress.setText("완료.");
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            progress.setTextColor(Color.BLACK);
            progress.setText("취소됨.");
        }
    }

    public void onClick(View v){
        if(v.getId() == R.id.start) {
            task = new myTask();
            task.execute(0);
        }
        else {
            task.cancel(true);
            task = null;
        }
    }
}
