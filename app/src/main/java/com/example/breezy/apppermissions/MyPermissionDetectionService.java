package com.example.breezy.apppermissions;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.List;

public class MyPermissionDetectionService extends Service {

    private static final String TAG = MyPermissionDetectionService.class.getCanonicalName();

    private IBinder myBinder = new MyBinder();
    private IAppInformationListener myListener;

    public MyPermissionDetectionService() {
    }

    public class MyBinder extends Binder {
        MyPermissionDetectionService getService() {
            return MyPermissionDetectionService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    public interface IAppInformationListener {
        public void onAppInformationRetrieved( List<AppInfo> appsReceived );
    }

    public void setPermissionRetrievalListener( IAppInformationListener listener )
        { myListener = listener; }

    public void queryPermissionDetectionServer( String query ) {
        if(query != null) {
            query.trim();
            if( query.contains(" ") ) {
                //HTTP Get request server doesn't do well with spaces
                //Replacing spaces with %20 and adding qutoes around the query with %22 as
                //suggested by stack overflow
                query = query.replaceAll("\\s", "%20");
                query = "%22" + query + "%22";
            }
            if( !query.isEmpty() ) {
                new GetAppPermissionsTask().execute(query);
            }
        }
    }

    public void removePermissionRetrievalListener() {
        myListener = null;
    }

    private class GetAppPermissionsTask extends AsyncTask<String, Double, List<AppInfo>> {

        @Override
        protected List<AppInfo> doInBackground(String... params) {
            String query = params[0];
            String Url = "http://52.5.30.209/googleplay-api-master/getapps.php?searchterm=" +
                    query;

            List<AppInfo> returnAppList = null;
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(Url);
                HttpResponse response = httpClient.execute(httpGet);
                InputStream istream = response.getEntity().getContent();
                BufferedInputStream bstream = new BufferedInputStream(istream);
                StringBuffer buffer = new StringBuffer();
                byte[] input = new byte[256];
                int len = 0;
                while( (len = istream.read(input)) != -1) {
                    buffer.append(new String(input, 0, len));
                }
                istream.close();
                Log.e("OUTPUT", "OUTPUT RESPONSE: " + buffer.toString());

                returnAppList = JsonParserUtility.parseAppInfoList(buffer.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }

            if(returnAppList != null) {
                /* Display results? */
            } else {
                Log.d(TAG, "Null return list");
            }
            return returnAppList;
        }


        @Override
        protected void onPostExecute(List<AppInfo> appInfoList) {
            super.onPostExecute(appInfoList);
            if( appInfoList != null ) {
                if (myListener != null) {
                    myListener.onAppInformationRetrieved(appInfoList);
                }
            } else {
                Toast.makeText(getApplicationContext(), "Query didn't return any results", Toast.LENGTH_SHORT).show();
            }

            //Pass this to next task
        }
    }

}
