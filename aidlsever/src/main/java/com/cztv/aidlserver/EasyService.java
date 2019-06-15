package com.cztv.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.cztv.IEasyService;

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
            System.out.println( "connect");
            LogUtils.i(TAG, "connect:   mes =" + mes);
        }

        @Override
        public void disConnect(String mes) {
            LogUtils.i(TAG, "disConnect:  mes =" + mes);
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
