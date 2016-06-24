package com.demo.myappmvp.data.http;


import com.demo.myappmvp.data.entity.BaseEntity;
import com.demo.myappmvp.data.entity.ProductEntity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Txw on 2016/6/23.
 */
public interface BlogService {
    @POST("api/FolderApi/GetMenue/{ContentFolderId}")
    Call<ResponseBody> getLingfoBlog(@Path("ContentFolderId") int id);

    @GET("api.aspx?action=Products&cmd=GetProductList&pl=2&token=111")
    Call<ProductEntity> getProductList();

    @GET("api.aspx")
    Call<ProductEntity> getProductList(@Query("action") String action, @Query("cmd") String cmd,
                                       @Query("pl") int pl, @Query("token") String token);

    @GET("api.aspx")
    Observable<ProductEntity> getPList(@Query("action") String action, @Query("cmd") String cmd,
                                       @Query("pl") int pl, @Query("token") String token);

    @GET("api.aspx")
    Observable<BaseEntity> getToken(@Query("action") String action, @Query("cmd") String cmd,
                                    @Query("pl") int pl, @Query("key") String key);

    @FormUrlEncoded
    @POST("api.aspx")
    Observable<BaseEntity> uploadPhoto(@Query("action") String action, @Query("cmd") String cmd,
                                       @Query("pl") int pl, @Query("token") String token,
                                       @Query("imageData") String imageData,
                                       @Query("aToken") String aToken);
}
