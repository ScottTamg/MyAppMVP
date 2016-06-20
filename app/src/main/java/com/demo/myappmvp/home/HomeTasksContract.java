package com.demo.myappmvp.home;

import android.support.annotation.NonNull;

import com.demo.myappmvp.BasePresenter;
import com.demo.myappmvp.BaseView;
import com.demo.myappmvp.entity.ProductEntity;

import java.util.List;

/**
 * Created by Txw on 2016/6/20.
 */
public class HomeTasksContract {

    interface View extends BaseView<Presenter> {
        void showData(List<ProductEntity.DataBean.ListBean> data);
        void showNoData();
        void showError(@NonNull String error);
    }

    interface Presenter extends BasePresenter {

    }
}
