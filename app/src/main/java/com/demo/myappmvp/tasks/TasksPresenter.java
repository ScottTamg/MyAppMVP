package com.demo.myappmvp.tasks;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.demo.myappmvp.data.Task;
import com.demo.myappmvp.data.source.TasksRepository;
import com.demo.myappmvp.utils.schedulers.BaseSchedulerProvider;

import rx.subscriptions.CompositeSubscription;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by Administrator on 2016/12/6.
 */

public class TasksPresenter implements TasksContract.Presenter {
    @NonNull
    private final TasksRepository mTasksRepository;

    @NonNull
    private final TasksContract.View mTasksView;

    @NonNull
    private final BaseSchedulerProvider mSchedulerProvider;

    @NonNull
    private TasksFilterType mCurrentFiltering = TasksFilterType.ALL_TASKS;

    private boolean mFirstLoad = true;

    @NonNull
    private CompositeSubscription mSubscriptions;

    public TasksPresenter(@NonNull TasksRepository tasksRepository,
                          @NonNull TasksContract.View tasksView,
                          @NonNull BaseSchedulerProvider schedulerProvider) {
        this.mTasksRepository = checkNotNull(tasksRepository, "tasksRepository cannot be null");
        this.mTasksView = checkNotNull(tasksView, "taskView cannot be null");
        this.mSchedulerProvider = checkNotNull(schedulerProvider, "schedulerProvider cannot be null");

        mSubscriptions = new CompositeSubscription();
        mTasksView.setPresenter(this);
    }


    @Override
    public void result(int requestCode, int resultCode) {
        if (AddEditTaskActivity.REQUEST_ADD_TASK == requestCode && Activity.RESULT_OK == resultCode) {
            mTasksView.showSuccessfullySavedMessage();
        }
    }

    @Override
    public void loadTasks(boolean forceUodate) {
        loadTasks(forceUodate || mFirstLoad, true);
        mFirstLoad = false;
    }

    private void loadTasks(final boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            mTasksView.setLoadingIndicator(true);
        }
        if (forceUpdate) {
            mTasksRepository.refreshTasks();
        }

        EspressoIdlingResource.increment();
    }

    @Override
    public void addNewTask() {

    }

    @Override
    public void openTaskDetails(@NonNull Task requestedTask) {

    }

    @Override
    public void completeTask(@NonNull Task completedTask) {

    }

    @Override
    public void activateTask(@NonNull Task activeTask) {

    }

    @Override
    public void clearCompletedTasks() {

    }

    @Override
    public void setFiltering(TasksFilterType requestType) {

    }

    @Override
    public TasksFilterType getFiltering() {
        return null;
    }

    @Override
    public void subscribe() {
        loadTasks(false);
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }
}
