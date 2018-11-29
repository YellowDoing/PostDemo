package cn.hg.common;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
//import com.orhanobut.logger.AndroidLogAdapter;
//import com.orhanobut.logger.Logger;

public class MyApplication extends Application {

    public static boolean isDebug = true; //true 测试版 false 正式版

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        //Logger.addLogAdapter(new AndroidLogAdapter());


        User user = new User();
        user.setToken("7b6f39e1ee25a8c5d7cfd61984db1034");
        user.setId(1);
        user.save();
    }
}
