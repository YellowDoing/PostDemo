package cn.hg.community;

import java.util.List;

import cn.hg.common.BaseResp;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface ApiService {


    @GET("/post")
    Call<BaseResp<List<Post>>> getPosts(@Header("page")int page);


}
