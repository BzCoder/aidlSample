package com.cztv.adilclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cztv.IEasyService;


/**
 * @author : BaoZhou
 * @date : 2019/1/26 15:08
 */
public class MainActivity extends AppCompatActivity {
    private static final String ACTION = "com.cztv.IEasyService";
    Button button;
    private IEasyService mIEasyService;
    ServiceConnection mServiceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mIEasyService = IEasyService.Stub.asInterface(service);
                Message msg = Message.obtain(null, 2);
                Bundle data = new Bundle();
                data.putString("msg", "hello,this is client");
                msg.setData(data);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mIEasyService = null;
            }
        };
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIEasyService != null) {
                    try {
                        mIEasyService.connect(" Client connect");
                        Toast.makeText(MainActivity.this, "mIEasyService.calculate():" + mIEasyService.calculate(), Toast.LENGTH_SHORT).show();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    bindService();
                }

            }
        });


    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setPackage("com.cztv.aidlserver");
        intent.setAction(ACTION);
        boolean b = bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        System.out.println(b);
    }

    @Override
    protected void onDestroy() {
        unbindService(mServiceConnection);
        super.onDestroy();
    }
}
