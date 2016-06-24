package com.demo.myappmvp.data.http;

import com.demo.myappmvp.base.MyApplication;
import com.demo.myappmvp.data.entity.BaseEntity;
import com.demo.myappmvp.data.entity.ProductEntity;
import com.demo.myappmvp.utils.SPUtil;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Txw on 2016/6/23.
 */
public class RetrofitUtil {
    private static final String TEXT_BASIC_WWW_URL = "http://api99.changbaishan.com/";
    private static final int PL = 2;
    private static final String ACTION_PRODUCTS = "Products";
    private static final String ACTION_TOKEN = "Token";
    private static final String CMD_PRODUCTS = "GetProductList";
    private static final String CMD_TOKEN = "getToken";
    private static final String KEY = "28101be64166ea4d150ba6a5cce6f8c8";

    private volatile static RetrofitUtil mInstance;
    private Retrofit mRetrofit;
    private BlogService mService;
    private SPUtil mSPUtil;

    private RetrofitUtil() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(TEXT_BASIC_WWW_URL)
                .client(OkHttpClientUtil.getInstance().getOkHttpClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = mRetrofit.create(BlogService.class);
        mSPUtil = MyApplication.getInstance().getSpUtil();
    }

    public static RetrofitUtil getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitUtil.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitUtil();
                }
            }
        }
        return mInstance;
    }

    public void getPList(Subscriber<ProductEntity> subscriber) {
        mService.getPList(ACTION_PRODUCTS, CMD_PRODUCTS, PL, (String) mSPUtil.get(SPUtil.TOKEN, ""))
                .subscribeOn(Schedulers.newThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getToken(Subscriber<BaseEntity> subscriber) {
        mService.getToken(ACTION_TOKEN, CMD_TOKEN, PL, KEY)
                .subscribeOn(Schedulers.newThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
