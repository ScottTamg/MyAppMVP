package com.demo.myappmvp.addedittask;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.demo.myappmvp.R;

/**
 * Created by Txw on 2017/1/3.
 */
public class AddEditTaskActivity extends AppCompatActivity{

    public static final int REQUEST_ADD_TASK = 1;

    public static final String SHOULD_LOAD_DATA_FROM_REPO_KEY = "SHOULD_LOAD_DATA_FROM_REPO_KEY";

    private AddEditTaskPresenter mAddEditTaskPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtask_act);


    }
}
