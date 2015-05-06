package com.example.breezy.apppermissions;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private Button mCategorySearchButton;
    private EditText mCategoryEditText;
    private MyPermissionDetectionService myPermissionService;
    private MyPermissionDetectionService.IAppInformationListener myIAppInfoListener;

    private boolean mBound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCategorySearchButton = (Button) findViewById(R.id.btsubmit);
        mCategoryEditText = (EditText) findViewById(R.id.etcategory);
        mCategorySearchButton.setOnClickListener(this);
        myIAppInfoListener = new MyPermissionDetectionService.IAppInformationListener() {
            @Override
            public void onAppInformationRetrieved(List<AppInfo> appsReceived) {

            }
        };
        mBound = false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch( v.getId() ) {
            case R.id.btsubmit:
                if( mBound ) {
                    String query = mCategoryEditText.getText().toString();
                    myPermissionService.queryPermissionDetectionServer(query);
                } else {
                    bindMyPermissionDetectionService();
                }
                break;
        }
    }

    private void bindMyPermissionDetectionService() {
        Intent boundIntent = new Intent(this, MyPermissionDetectionService.class);
        bindService(boundIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private void unbindMyPermissionDetectionService() {
        myPermissionService.removePermissionRetrievalListener();
        unbindService(mServiceConnection);
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyPermissionDetectionService.MyBinder binder =
                    (MyPermissionDetectionService.MyBinder) service;
            myPermissionService = binder.getService();
            myPermissionService.setPermissionRetrievalListener(myIAppInfoListener);
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {}
    };

    @Override
    protected void onStart() {
        super.onStart();
        if(!mBound) {
            bindMyPermissionDetectionService();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mBound) {
            unbindMyPermissionDetectionService();
            mBound = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if( !mBound ) {
            bindMyPermissionDetectionService();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        //Unbind from the service
        if (mBound) {
            unbindMyPermissionDetectionService();
            mBound = false;
        }
    }
}