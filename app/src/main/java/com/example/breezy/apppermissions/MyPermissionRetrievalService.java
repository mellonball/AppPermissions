package com.example.breezy.apppermissions;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MyPermissionRetrievalService extends Service {

    private IBinder myBinder = new MyBinder();

    public MyPermissionRetrievalService() {
    }

    public class MyBinder extends Binder {
        MyPermissionRetrievalService getService() {
            return MyPermissionRetrievalService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    public interface IPermissionRetrievalListener {

    }


}
