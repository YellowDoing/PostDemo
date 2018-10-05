package cn.hg.community;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {

    private static Retrofit sRetrofit;
    private final static String url = "http://192.168.1.4:8080";

    static ApiService create(){
        if (sRetrofit == null){
            //声明日志类
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
            okHttpClient.addInterceptor(httpLoggingInterceptor);

            sRetrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(url)
                    .client(okHttpClient.build())
                    .build();
        }

        return sRetrofit.create(ApiService.class);
    }
}
