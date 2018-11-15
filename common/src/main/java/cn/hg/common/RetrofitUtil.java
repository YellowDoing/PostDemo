package cn.hg.common;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {

    private final static String URL = "http://192.168.1.17:8080";
    private final static String TEST_URL = "http://192.168.1.17:8080";

    public static Retrofit newInstance() {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
        if (MyApplication.isDebug) {
            //声明日志类
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
            okHttpClient.addInterceptor(httpLoggingInterceptor);
            retrofitBuilder.client(okHttpClient.build());
        }

        return retrofitBuilder.addConverterFactory(GsonConverterFactory.create())
                .baseUrl(MyApplication.isDebug ? TEST_URL :URL)
                .build();
    }
}
