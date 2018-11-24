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
        user.setToken("7b7fefe3e6efe762d505294590cc3e7a");
        user.setNickname("黄干");
        user.setAvatar("guangzhou.myqcloud.com/community/picture/hg1538880960132.jpg");
        user.setUsername("huanggan");
        user.save();
    }
}
