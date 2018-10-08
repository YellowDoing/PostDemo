package cn.hg.community;

import java.util.List;

import cn.hg.common.BaseResp;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {


    @GET("/post")
    Call<BaseResp<List<Post>>> getPosts(@Header("page")int page);


    @POST("/post")
    Call<BaseResp> publish(@Header("token")String token, @Body Post post);


    /**
     * 点赞
     */
    @FormUrlEncoded
    @PATCH("/post/{id}/great")
    Call<BaseResp> great(@Header("token")String token, @Path("id")String id);

}
