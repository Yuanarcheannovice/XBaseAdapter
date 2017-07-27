package com.xz.util;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2017/7/27 0027.
 */

public class DemoApplication extends Application {
    public static Context context = null;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
