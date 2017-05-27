package com.ivsa.multi;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class M extends AppCompatActivity {
    EditText count;
    ImageView img;
    TextView time;
    myTask task;
    int index = 0;
    Boolean clicked = false;
    String[] names = {"햄버거","피자","치킨","스파게티","샐러드"};
    int[] draw = {R.drawable.hamburger, R.drawable.pizza, R.drawable.chicken,
                  R.drawable.spagetti,  R.drawable.salad};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("오늘의 메뉴");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m);
        count = (EditText) findViewById(R.id.ecount);
        img = (ImageView) findViewById(R.id.imageView);
        time = (TextView) findViewById(R.id.ttime);
    }

    class myTask extends AsyncTask<Integer,Integer,Integer> {
        @Override
        protected Integer doInBackground(Integer... params) {
            for(int i = 1; ; i++) {
                if (isCancelled()) return null;
                else {
                    try {
                        Thread.sleep(1000);
                        if (clicked) return i;
                        publishProgress(i, params[0]);
                     } catch(InterruptedException e){
                        e.printStackTrace();
                        }
                }
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if (values[0] % values[1] == 0) {
                index = (values[0]/values[1])% draw.length;
                img.setImageResource(draw[index]);
            }
            time.setVisibility(View.VISIBLE);
            time.setText("시작부터 " + values[0] + "초");

        }

        @Override
        protected void onPostExecute(Integer integer) {
            clicked = false;
            time.setText(names[index]+"선택"+"("+ (integer-1) +"초"+")");
        }
    }

    public void onClick(View v){
        if(v.getId() == R.id.imageView) {
            if (task == null) {
                task = new myTask();
                task.execute(Integer.parseInt(count.getText().toString()));
            }
            else {
                clicked = true;
            }
        }
        else {
            task.cancel(true);
            task = null;
            img.setImageResource(R.drawable.fork);
            time.setText("");
            time.setVisibility(View.INVISIBLE);
        }
    }
}
