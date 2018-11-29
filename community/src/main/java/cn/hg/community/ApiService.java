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
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {


    @GET("/post")
    Call<BaseResp<List<Post>>> getPosts(@Header("page")int page);


    /**
     * 发帖
     */
    @POST("/post")
    Call<BaseResp> publish(@Body Post post);


    /**
     * 点赞
     */
    @Multipart
    @POST("/great/{id}")
    Call<BaseResp> great( @Path("id")int id,@Field("user_id")int user_id);

}
