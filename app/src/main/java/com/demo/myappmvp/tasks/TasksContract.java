package com.demo.myappmvp.tasks;

import android.support.annotation.NonNull;

import com.demo.myappmvp.BasePresenter;
import com.demo.myappmvp.BaseView;
import com.demo.myappmvp.data.Task;

import java.util.List;

/**
 * Created by Administrator on 2016/12/6.
 */

public interface TasksContract {
    interface View extends BaseView<Presenter> {
        void serLoadingIndicatior(boolean active);

        void showTasks(List<Task> tasks);

        void showAddTask();

        void showTaskDetailsUi(String taskId);

        void showTaskMarkedComplete();

        void showTaskMarkedActive();

        void showCompletedTasksCleared();

        void showLoadingTasksError();

        void showNoTasks();

        void showActiveFilterLabel();

        void showCompletedFilterLabel();

        void showAllFilterLabel();

        void showNoActiveTasks();

        void showNoCompletedTasks();

        void showSuccessfullySavedMessage();

        boolean isActive();

        void showFilteringPopUpMenu();

        void setLoadingIndicator(boolean b);
    }

    interface Presenter extends BasePresenter{
        void result(int requestCode, int resultCode);

        void loadTasks(boolean forceUodate);

        void addNewTask();

        void openTaskDetails(@NonNull Task requestedTask);

        void completeTask(@NonNull Task completedTask);

        void activateTask(@NonNull Task activeTask);

        void clearCompletedTasks();

        void setFiltering(TasksFilterType requestType);

        TasksFilterType getFiltering();
    }
}
