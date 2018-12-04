package cn.hg.common;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.lzy.ninegrid.NineGridView;
//import com.orhanobut.logger.AndroidLogAdapter;
//import com.orhanobut.logger.Logger;

public class MyApplication extends Application {

    public static boolean isDebug = true; //true 测试版 false 正式版
    private static MyApplication sMyApplication;


    @Override
    public void onCreate() {
        super.onCreate();
        sMyApplication = this;
        Utils.init(this);
        //Logger.addLogAdapter(new AndroidLogAdapter());
        User user = new User();
        user.setToken("c632bfdb4f3cc9d4d6e19ee3f9d1d22c");
        user.setId(1);
        user.save();


        NineGridView.setImageLoader(new NineGridView.ImageLoader() {
            @Override
            public void onDisplayImage(Context context, ImageView imageView, String url) {
                Glide.with(context).load(url).into(imageView);
            }

            @Override
            public Bitmap getCacheImage(String url) {
                return null;
            }
        });
    }
}
