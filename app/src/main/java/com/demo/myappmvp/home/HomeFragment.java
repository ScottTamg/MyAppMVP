package com.demo.myappmvp.home;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.myappmvp.R;
import com.demo.myappmvp.data.entity.ProductEntity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Txw on 2016/6/20.
 */
public class HomeFragment extends Fragment implements HomeTasksContract.View {
    @Bind(R.id.iv_left)
    ImageView mIvLeft;
    @Bind(R.id.tv_left)
    TextView mTvLeft;
    @Bind(R.id.ll_left)
    LinearLayout mLlLeft;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.btn_right)
    Button mBtnRight;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    private HomeTasksContract.Presenter mPresenter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        mLlLeft.setVisibility(View.VISIBLE);
        mIvLeft.setImageResource(R.drawable.location);
        mBtnRight.setVisibility(View.VISIBLE);
        mSwipeRefresh.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.ll_left, R.id.btn_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                break;
            case R.id.btn_right:
                break;
        }
    }

    @Override
    public void setPresenter(HomeTasksContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showData(List<ProductEntity.DataBean.ListBean> data) {

    }

    @Override
    public void showNoData() {
        Toast.makeText(getActivity(), "NoData", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(@NonNull String error) {
        Toast.makeText(HomeFragment.this.getActivity(), error, Toast.LENGTH_SHORT).show();
    }
}
