package com.example.user.simpleui;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by user on 2016/8/16.
 */
public class SimpleUIApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                        .applicationId("76ee57f8e5f8bd628cc9586e93d428d5")
                        .server("http://parseserver-ps662-env.us-east-1.elasticbeanstalk.com/parse/")
                        .clientKey("KqSNLnRMexM9ZCBCuMUEUk3LZSJlmURKhipVe83D")
                        .build()
        );
    }
}
