package com.demo.myappmvp.home;

import com.demo.myappmvp.base.MyApplication;
import com.demo.myappmvp.entity.ProductEntity;
import com.demo.myappmvp.http.VolleyRequest;
import com.demo.myappmvp.http.VolleyRequestListener;
import com.demo.myappmvp.utils.LoadDataUtil;
import com.demo.myappmvp.utils.SPUtil;

/**
 * Created by Txw on 2016/6/20.
 */
public class HomePresenter implements HomeTasksContract.Presenter {

    private HomeTasksContract.View mView;

    public HomePresenter(HomeTasksContract.View view) {
        this.mView = view;

        mView.setPresenter(this);
    }

    @Override
    public void start() {
        getProductList();
    }

    private void getProductList() {
        VolleyRequest.getProductList("ProductList", new VolleyRequestListener() {
            @Override
            public void success(boolean isSuccess, String response, String error) {
                if (isSuccess) {
                    ProductEntity entity =
                            LoadDataUtil.getInstance().getJsonData(response, ProductEntity.class);
                    if (0 == entity.getErrorNo()) {
                        mView.showData(entity.getData().getList());
                    } else if (-99 == entity.getErrorNo()) {
                        MyApplication.getInstance().getSpUtil().put(SPUtil.TOKEN, "");
                        getProductList();
                    } else {
                        mView.showError(entity.getMessage());
                    }
                } else {
                    mView.showError(error);
                }
            }
        });
    }
}
