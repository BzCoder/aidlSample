// IMyAidlInterface.aidl
package com.cztv;

// Declare any non-default types here with import statements

interface IEasyService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     void connect(String mes);
     void disConnect(String mes);
     int calculate();
}
