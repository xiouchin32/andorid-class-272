package com.example.user.simpleui;

import android.os.AsyncTask;

/**
 * Created by user on 2016/8/18.
 */
public class GeoCodingTask extends AsyncTask<String,Void,double[]>{
//String Background的型態是依照上面那行而定 回傳東西是看double[]
    @Override
    protected double[] doInBackground(String... params) {
        //doInBackground 不是主要執行緒 是另一個在做的 等待DATA回來是在這裡做

        return new double[0];
    }

    @Override
    protected void onPostExecute(double[] doubles) {
        super.onPostExecute(doubles);
        //onPostExecute 可以更改UI 所以他是MAINTHREAD在做的  資料回來時在這裡做
    }
}
