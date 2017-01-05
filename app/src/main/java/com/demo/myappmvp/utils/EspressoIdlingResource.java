package com.demo.myappmvp.utils;

import android.support.test.espresso.IdlingResource;

/**
 * Created by Administrator on 2016/12/7.
 */

public class EspressoIdlingResource {
    private static final String RESOURE = "GLOBAL";

    private static SimpleCountingIdlingResource mSimpleCountingIdlingResource =
            new SimpleCountingIdlingResource(RESOURE);

    public static void increment() {
        mSimpleCountingIdlingResource.increment();
    }

    public static void decrement() {
        mSimpleCountingIdlingResource.decrement();
    }

    public static IdlingResource getidlingResource() {
        return mSimpleCountingIdlingResource;
    }
}
