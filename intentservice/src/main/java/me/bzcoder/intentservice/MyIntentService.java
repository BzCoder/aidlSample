package me.bzcoder.intentservice;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyIntentService extends IntentService {

    public static final String TAG = "ServiceSample";

    public MyIntentService() {
        super("MyIntentService");
    }

    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(Constant.ACTION_FOO);
        intent.putExtra(Constant.EXTRA_PARAM1, param1);
        intent.putExtra(Constant.EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(Constant.ACTION_BAZ);
        intent.putExtra(Constant.EXTRA_PARAM1, param1);
        intent.putExtra(Constant.EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Log.i(TAG, Thread.currentThread().getName());
            final String action = intent.getAction();
            if (Constant.ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(Constant.EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(Constant.EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (Constant.ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(Constant.EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(Constant.EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
            long endTime = System.currentTimeMillis() + 20 * 1000;
            Log.i(TAG, "----IntentService耗时任务执行开始---");
            while (System.currentTimeMillis() < endTime) {
                synchronized (this) {
                    try {
                        wait(endTime - System.currentTimeMillis());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            Log.i(TAG, "----IntentService耗时任务执行完成---");
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        Log.i(TAG, "handleActionFoo: " + param1 + " " + param2);
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        Log.i(TAG, "handleActionBaz: " + param1 + " " + param2);
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "IntentService onCreate");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "IntentService onDestroy");
    }
}