package com.example.user.simpleui;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by user on 2016/8/16.
 */
public class SimpleUIApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Drink.class);//要先註冊一個客製化的PARSEOBJECT
        Parse.initialize(new Parse.Configuration.Builder(this)
                        .applicationId("oFQuZethAdtk8ncRGZvtqkLSu9YnNEIBJ0vLJ6Rt")
                        .server("https://parseapi.back4app.com/")
                        .clientKey("F07EXceCtcMtdDT0CafN8f1O7FyaQWoFI0J3V3FE")
                        .build()
        );
    }
}
