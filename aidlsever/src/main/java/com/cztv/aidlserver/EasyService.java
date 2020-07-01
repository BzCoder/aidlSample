package com.cztv.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.cztv.IEasyService;

import java.util.Random;

/**
 * @author : BaoZhou
 * @date : 2019/1/26 16:22
 */
public class EasyService extends Service {
    private static final String TAG = "EasyService";

    public EasyService() {
    }

    IEasyService.Stub mIBinder = new IEasyService.Stub() {
        @Override
        public void connect(String mes) {
            Log.d(TAG, "服务器连接成功:" + mes);
        }

        @Override
        public void disConnect(String mes) {
            Log.d(TAG, "服务器断开成功:" + mes);
        }

        @Override
        public int calculate() {
            Random random = new Random();
            Log.d(TAG, "正在计算");
            return random.nextInt(30);
        }

        @Override
        public void syncCalculate() throws RemoteException {
            long startTime = System.currentTimeMillis();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long castTime = System.currentTimeMillis() - startTime;
            Log.d(TAG, "异步进程计算好了" + " 耗时：" + castTime);
        }


    };

    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("绑定成功");
        LogUtils.i(TAG, "onBind:   intent = " + intent.toString());
        return mIBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtils.i(TAG, "onUnbind:   =");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        LogUtils.i(TAG, "onDestroy:   =");
        super.onDestroy();
    }
}
