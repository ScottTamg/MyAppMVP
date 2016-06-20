package com.demo.myappmvp;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.demo.myappmvp.adapter.SectionsPagerAdapter;
import com.demo.myappmvp.widget.dragindicator.DragIndicatorView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.tab_content)
    ViewPager mTabContent;
    @Bind(R.id.tab_rb_hone)
    RadioButton mTabRbHone;
    @Bind(R.id.tab_rb_shoppingcart)
    RadioButton mTabRbShoppingcart;
    @Bind(R.id.tab_rb_account)
    RadioButton mTabRbAccount;
    @Bind(R.id.tabs_rg)
    RadioGroup mTabsRg;
    @Bind(R.id.indicator)
    DragIndicatorView mIndicator;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mTabContent.setAdapter(mSectionsPagerAdapter);
        mTabContent.setOffscreenPageLimit(2);
        mTabContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mTabsRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < mTabsRg.getChildCount(); i++) {
                    if (mTabsRg.getChildAt(i).getId() == checkedId) {
                        mTabContent.setCurrentItem(i);
                        break;
                    }
                }
            }
        });
    }
}
