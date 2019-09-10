package me.bzcoder.leak;

import android.content.Context;

public class SingletonManager {
        private Context mContext;
        private static SingletonManager instance;
     
        private SingletonManager(Context context){
            mContext = context;
        }
     
        public static synchronized SingletonManager getInstance(Context context){
            if(instance == null){
                instance = new SingletonManager(context);
            }
            return instance;
        }
    }