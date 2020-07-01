package me.bzcoder.intentservice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    public static final String TAG = "ServiceSample";

    public static void startMyService(Context context, String param1, String param2) {
        Intent intent = new Intent(context, MyService.class);
        intent.setAction(Constant.ACTION_FOO);
        intent.putExtra(Constant.EXTRA_PARAM1, param1);
        intent.putExtra(Constant.EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final String param1 = intent.getStringExtra(Constant.EXTRA_PARAM1);
        final String param2 = intent.getStringExtra(Constant.EXTRA_PARAM2);
        // 该方法内执行耗时任务可能导致ANR(Application Not Responding)异常
        long endTime = System.currentTimeMillis() + 2 * 1000;
        Log.i(TAG, "----耗时任务执行开始---");
        while (System.currentTimeMillis() < endTime) {
            synchronized (this) {
                try {
                    wait(endTime - System.currentTimeMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.i(TAG, "----耗时任务执行完成---");
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "Service onCreate");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Service onDestroy");
    }
} 