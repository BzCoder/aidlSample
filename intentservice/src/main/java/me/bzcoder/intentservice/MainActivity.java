package me.bzcoder.intentservice;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyIntentService.startActionBaz(this,"param1","param2");
        MyIntentService.startActionFoo(this,"param1","param2");
        MyService.startMyService(this,"param1","param2");
    }


}