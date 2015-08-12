package com.waf.soma.wearefamily;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.Vector;

public class MainService extends Service {

    LocationManager mLocMan;
    String mProvider;
    String TAG="TEST";
    int mCount;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(TAG, "onCreate 호출");
        mLocMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mProvider = mLocMan.getBestProvider(new Criteria(),true);

        //원래 OnResume에 있던 코드
        mLocMan.requestLocationUpdates(mProvider, 3000, 10, mListener);
    }

    /*
    public void onResume(){
        super.onResume();
        mCount=0;
        //
        Log.i(TAG,"현재 상태 : 서비스 시작");
    }

    public void onPause(){
        super.onPause();
        mLocMan.removeUpdates(mListener);
        Log.i(TAG,"현재 상태 : 서비스 정지");
    }
    */

    LocationListener mListener = new LocationListener(){
        public void onLocationChanged(Location location){
            mCount++;
            String sloc = String.format("수신회수:%d 위도:%f 경도:%f 고도:%f",
                    mCount,location.getLatitude(),location.getLongitude(),location.getAltitude());
            Log.i(TAG,sloc);

            //서버로 위치정보 post
            Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
            nameValue.add(new BasicNameValuePair("latitude", Double.toString(location.getLatitude())));
            nameValue.add(new BasicNameValuePair("longitude",Double.toString(location.getLongitude())));
            nameValue.add(new BasicNameValuePair("altitude",Double.toString(location.getAltitude())));

            new HttpTask().executor(/*nameValue*/);

        }
        public void onProviderDisabled(String provider){
            Log.i(TAG,"현재 상태 : 서비스 사용 불가");
        }

        public void onProviderEnabled(String provider){
            Log.i(TAG,"현재 상태 : 서비스 사용 가능");
        }

        public void onStatusChanged(String provider,int status,Bundle extras){
            String sStatus="";
            switch(status){
                case LocationProvider.OUT_OF_SERVICE:
                    sStatus="범위 벗어남";
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    sStatus="범위 벗어남";
                    break;
                case LocationProvider.AVAILABLE:
                    sStatus="사용 가능";
                    break;
            }
            Log.i(TAG, provider+"상태 변경 : "+sStatus);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");

        return null;
    }
}
