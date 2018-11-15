package cn.hg.community;

import cn.hg.common.RetrofitUtil;
import retrofit2.Retrofit;

public class Api {

    private static ApiService sApiService;

    public static ApiService instance(){
        if (sApiService == null)
            sApiService = RetrofitUtil.newInstance().create(ApiService.class);
        return sApiService;
    }
}
