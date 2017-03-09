package com.rivc.simplepost;

import android.app.Application;

/**
 * Created by Riven on 2017/3/1.
 * Email: 1819485687@qq.com
 */

public class MyApplication extends Application {

    private static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static MyApplication getInstance() {
        if (myApplication == null) {
            myApplication = new MyApplication();
        }
        return myApplication;
    }
}
