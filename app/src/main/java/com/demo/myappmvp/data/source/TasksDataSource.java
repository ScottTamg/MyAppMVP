package com.demo.myappmvp.data.source;

import android.support.annotation.NonNull;

import com.demo.myappmvp.data.Task;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2016/12/6.
 */

public interface TasksDataSource {
    Observable<List<Task>> getTasks();

    Observable<Task> getTasks(@NonNull String taskId);

    void saveTask(@NonNull Task task);

    void completeTask(@NonNull Task task);

    void completeTask(@NonNull String taskId);

    void activateTask(@NonNull Task task);

    void activateTask(@NonNull String taskId);

    void clearCompletedTasks();

    void refreshTasks();

    void deleteAllTasks();

    void deleteTask(@NonNull String taskId);
}
