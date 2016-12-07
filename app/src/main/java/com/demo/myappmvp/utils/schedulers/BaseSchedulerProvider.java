package com.demo.myappmvp.utils.schedulers;

import android.support.annotation.NonNull;

import rx.Scheduler;

/**
 * Created by Administrator on 2016/12/7.
 */

public interface BaseSchedulerProvider {
    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();
}
