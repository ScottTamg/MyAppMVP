package com.demo.myappmvp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.demo.myappmvp.home.HomeFragment;

/**
 * Created by Txw on 2016/4/5.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private static final int HOMEFRAGMENT = 0;
    private static final int SHOPPINGCARTFRAGMENT = 1;
    private static final int ACCOUNTFRAGMENT = 2;

    private HomeFragment mHomeFragment;
    private ShoppingCartFragment mShoppingCartFragment;
    private AccountFragment mAccountFragment;

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position) {
            case HOMEFRAGMENT:
                return mHomeFragment = (null == mHomeFragment) ? HomeFragment.newInstance()
                        : mHomeFragment;
            case SHOPPINGCARTFRAGMENT:
                return mShoppingCartFragment = (null == mShoppingCartFragment) ? ShoppingCartFragment.newInstance()
                        : mShoppingCartFragment;
            case ACCOUNTFRAGMENT:
                return mAccountFragment = (null == mAccountFragment) ? AccountFragment.newInstance()
                        : mAccountFragment;
            default:
                return mHomeFragment = (null == mHomeFragment) ? HomeFragment.newInstance()
                        : mHomeFragment;
        }

    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case HOMEFRAGMENT:
                return "SECTION 1";
            case SHOPPINGCARTFRAGMENT:
                return "SECTION 2";
            case ACCOUNTFRAGMENT:
                return "SECTION 3";
        }
        return null;
    }

    public HomeFragment getHomeFragment() {
        return mHomeFragment;
    }

    public ShoppingCartFragment getShoppingCartFragment() {
        return mShoppingCartFragment;
    }

    public AccountFragment getAccountFragment() {
        return mAccountFragment;
    }
}
