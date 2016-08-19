package com.example.user.simpleui;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;

/**
 * Created by user on 2016/8/18.
 */
public class GeoCodingTask extends AsyncTask<String,Void,double[]>{
//String Background的型態是依照上面那行而定 回傳東西是看double[]
    WeakReference<GeocodingCallback>geocodingCallbackWeakReference;
    //如果沒有他有可能會卡在DO IN BACK GROUND 怕會卡住ACTIVITY的資源
    @Override
    protected double[] doInBackground(String... params) {
        //doInBackground 不是主要執行緒 是另一個在做的 等待DATA回來是在這裡做

        return Utils.getLatLngFromAddress(params[0]);
    }

    @Override
    protected void onPostExecute(double[] doubles) {
        super.onPostExecute(doubles);
        //onPostExecute 可以更改UI 所以他是MAINTHREAD在做的  資料回來時在這裡做
        if( geocodingCallbackWeakReference.get()!=null)
        {
            GeocodingCallback geocodingCallback = geocodingCallbackWeakReference.get();
            geocodingCallback.done(doubles);
        }

    }
    public  GeoCodingTask(GeocodingCallback geocodingCallback)
    {
        geocodingCallbackWeakReference = new WeakReference<GeocodingCallback>(geocodingCallback);
    }
    interface GeocodingCallback
    {
        void done(double[] latlng);
    }
}
