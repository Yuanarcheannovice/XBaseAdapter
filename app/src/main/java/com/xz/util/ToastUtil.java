package com.xz.util;

import android.widget.Toast;

/**
 * Created by Administrator on 2017/6/15 0015.
 */

public class ToastUtil {

    public static void show(String str){
        Toast.makeText(DemoApplication.context,str,Toast.LENGTH_SHORT).show();
    }
}
