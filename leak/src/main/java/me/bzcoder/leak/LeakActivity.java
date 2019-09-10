package me.bzcoder.leak;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
/**
 * 
 * @author : BaoZhou
 * @date : 2019/9/4 9:56
 */
public class LeakActivity extends AppCompatActivity {
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 10000);
    }
}