package me.bzcoder.foregroundservice;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this ,MonitorService.class);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
         startForegroundService(intent);
        }
        else{
            startService(intent);
        }

    }
}
