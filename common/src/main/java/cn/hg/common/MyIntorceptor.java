package cn.hg.common;

import com.blankj.utilcode.util.SPUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class MyIntorceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {

        Request original = chain.request();
        Request request = original.newBuilder()
                .header("token", SPUtils.getInstance().getString("token"))
                .method(original.method(), original.body())
                .build();

        return chain.proceed(request);
    }
}
