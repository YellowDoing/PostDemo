package cn.hg.common;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);

        User user = new User();
        user.setToken("7b7fefe3e6efe762d505294590cc3e7a");
        user.setNickname("黄干");
        user.setAvatar("guangzhou.myqcloud.com/community/picture/hg1538880960132.jpg");
        user.setUsername("huanggan");
        user.save();
    }
}
