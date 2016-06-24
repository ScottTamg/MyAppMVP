package com.demo.myappmvp.data;

import com.demo.myappmvp.base.MyApplication;
import com.demo.myappmvp.data.entity.BaseEntity;
import com.demo.myappmvp.data.entity.ProductEntity;
import com.demo.myappmvp.data.http.RetrofitUtil;
import com.demo.myappmvp.utils.SPUtil;

import rx.Subscriber;

/**
 * Created by Txw on 2016/6/21.
 */
public class ProductData {

    public void getData() {
//        getProductList();
        getPList();
    }

    private void getPList() {
        Subscriber<ProductEntity> subscriber = new Subscriber<ProductEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ProductEntity productEntity) {
                if (0 == productEntity.getErrorNo()) {

                } else if (-99 == productEntity.getErrorNo()) {
                    MyApplication.getInstance().getSpUtil().put(SPUtil.TOKEN, "");
                    getPList();
                } else {
                    productEntity.getMessage();
                }
            }
        };
        RetrofitUtil.getInstance().getPList(subscriber);
    }

    private void getToken() {
        Subscriber<BaseEntity> subscriber = new Subscriber<BaseEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseEntity baseEntity) {
                if (0 == baseEntity.getErrorNo()) {
                    MyApplication.getInstance().getSpUtil().put(SPUtil.TOKEN, baseEntity.getMessage());
                } else {
                    MyApplication.getInstance().getSpUtil().put(SPUtil.TOKEN, "");
                }
            }
        };
        RetrofitUtil.getInstance().getToken(subscriber);
    }
//    private void getProductList() {
//        VolleyRequest.getProductList("ProductList", new VolleyRequestListener() {
//            @Override
//            public void success(boolean isSuccess, String response, String error) {
//                if (isSuccess) {
//                    ProductEntity entity =
//                            LoadDataUtil.getInstance().getJsonData(response, ProductEntity.class);
//                    if (0 == entity.getErrorNo()) {
//                        mView.showData(entity.getData().getList());
//                    } else if (-99 == entity.getErrorNo()) {
//                        MyApplication.getInstance().getSpUtil().put(SPUtil.TOKEN, "");
//                        getProductList();
//                    } else {
//                        mView.showError(entity.getMessage());
//                    }
//                } else {
//                    mView.showError(error);
//                }
//            }
//        });
//    }
}
