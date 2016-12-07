package com.demo.myappmvp.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.demo.myappmvp.data.Task;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import rx.Observable;
import rx.functions.Func1;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by Administrator on 2016/12/6.
 */

public class TasksRepository implements TasksDataSource {

    @Nullable
    private static TasksRepository INSTANCE = null;

    @NonNull
    private final TasksDataSource mTasksRemoteDataSource;

    @NonNull
    private final TasksDataSource mTasksLocalDataSource;

    @VisibleForTesting
    @Nullable
    Map<String, Task> mCachedTasks;

    @VisibleForTesting
    boolean mCacheIsDirty = false;

    private TasksRepository(@NonNull TasksDataSource mTasksRemoteDataSource, @NonNull TasksDataSource mTasksLocalDataSource) {
        this.mTasksRemoteDataSource = mTasksRemoteDataSource;
        this.mTasksLocalDataSource = mTasksLocalDataSource;
    }

    public static TasksRepository getInstance(@NonNull TasksDataSource tasksRemoteDataSource,
                                              @NonNull TasksDataSource tasksLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new TasksRepository(tasksRemoteDataSource, tasksLocalDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public Observable<List<Task>> getTasks() {
        if (mCachedTasks != null && !mCacheIsDirty) {
            return Observable.from(mCachedTasks.values()).toList();
        } else if (mCachedTasks == null) {
            mCachedTasks = new LinkedHashMap<>();
        }

        Observable<List<Task>> remoteTasks = getAndSaveRemoteTasks();

        if (mCacheIsDirty) {
            return remoteTasks;
        } else {
            Observable<List<Task>> localTasks = getAndCacheLocalTasks();
            return Observable.concat(localTasks, remoteTasks)
                    .filter(tasks -> !tasks.isEmpty())
                    .first();
        }
    }

    private Observable<List<Task>> getAndCacheLocalTasks() {
        return mTasksLocalDataSource.getTasks()
                .flatMap(new Func1<List<Task>, Observable<List<Task>>>() {
                    @Override
                    public Observable<List<Task>> call(List<Task> tasks) {
                        return Observable.from(tasks)
                                .doOnNext(task -> mCachedTasks.put(task.getId(), task))
                                .toList();
                    }
                });
    }

    private Observable<List<Task>> getAndSaveRemoteTasks() {
        return mTasksLocalDataSource.getTasks()
                .flatMap(new Func1<List<Task>, Observable<List<Task>>>() {
                    @Override
                    public Observable<List<Task>> call(List<Task> tasks) {
                        return Observable.from(tasks).doOnNext(task -> mCachedTasks.put(task.getId(), task)).toList();
                    }
                });
    }

    @Override
    public Observable<Task> getTasks(@NonNull final String taskId) {
        checkNotNull(taskId);

        final Task cachedTask = getTaskWithId(taskId);

        if (cachedTask != null) {
            return Observable.just(cachedTask);
        }

        if (mCachedTasks == null) {
            mCachedTasks = new LinkedHashMap<>();
        }

        Observable<Task> localTask = getTaskWithIdFromLoaclRepository(taskId);
        Observable<Task> remoteTask = mTasksRemoteDataSource.getTasks(taskId)
                .doOnNext(task -> {
                    mTasksLocalDataSource.saveTask(task);
                    mCachedTasks.put(task.getId(), task);
                });

        return Observable.concat(localTask, remoteTask).first()
                .map(task -> {
                    if (task == null) {
                        throw new NoSuchElementException("No task found with taskid " + taskId);
                    }
                    return task;
                });
    }

    private Observable<Task> getTaskWithIdFromLoaclRepository(final String taskId) {
        return mTasksLocalDataSource.getTasks(taskId)
                .doOnNext(task -> mCachedTasks.put(taskId, task))
                .first();
    }

    @Override
    public void saveTask(@NonNull Task task) {
        checkNotNull(task);
        mTasksRemoteDataSource.saveTask(task);
        mTasksLocalDataSource.saveTask(task);

        if (mCachedTasks == null) {
            mCachedTasks = new LinkedHashMap<>();
        }
        mCachedTasks.put(task.getId(), task);
    }

    @Override
    public void completeTask(@NonNull Task task) {
        checkNotNull(task);
        mTasksRemoteDataSource.completeTask(task);
        mTasksLocalDataSource.completeTask(task);

        Task completedTask = new Task(task.getId(), task.getTitle(), task.getDescription(), true);

        if (mCachedTasks == null) {
            mCachedTasks = new LinkedHashMap<>();
        }
        mCachedTasks.put(task.getId(), completedTask);
    }

    @Override
    public void completeTask(@NonNull String taskId) {
        checkNotNull(taskId);
        Task taskWithId = getTaskWithId(taskId);
        if (taskWithId != null) {
            completeTask(taskWithId);
        }
    }

    private Task getTaskWithId(String taskId) {
        checkNotNull(taskId);
        if (mCachedTasks == null || mCachedTasks.isEmpty()) {
            return null;
        } else {
            return mCachedTasks.get(taskId);
        }
    }

    @Override
    public void activateTask(@NonNull Task task) {
        checkNotNull(task);
        mTasksRemoteDataSource.activateTask(task);
        mTasksLocalDataSource.activateTask(task);

        Task activeTask = new Task(task.getId(), task.getTitle(), task.getDescription());

        if (mCachedTasks == null) {
            mCachedTasks = new LinkedHashMap<>();
        }
        mCachedTasks.put(task.getId(), activeTask);
    }

    @Override
    public void activateTask(@NonNull String taskId) {
        checkNotNull(taskId);
        Task taskWithId = getTaskWithId(taskId);
        if (taskWithId != null) {
            activateTask(taskWithId);
        }
    }

    @Override
    public void clearCompletedTasks() {
        mTasksRemoteDataSource.clearCompletedTasks();
        mTasksLocalDataSource.clearCompletedTasks();

        if (mCachedTasks == null) {
            mCachedTasks = new LinkedHashMap<>();
        }
        Iterator<Map.Entry<String, Task>> it = mCachedTasks.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Task> entry = it.next();
            if (entry.getValue().isCompleted()) {
                it.remove();
            }
        }
    }

    @Override
    public void refreshTasks() {
        mCacheIsDirty = true;
    }

    @Override
    public void deleteAllTasks() {
        mTasksRemoteDataSource.deleteAllTasks();
        mTasksLocalDataSource.deleteAllTasks();

        if (mCachedTasks == null) {
            mCachedTasks = new LinkedHashMap<>();
        }
        mCachedTasks.clear();
    }

    @Override
    public void deleteTask(@NonNull String taskId) {
        mTasksRemoteDataSource.deleteTask(checkNotNull(taskId));
        mTasksLocalDataSource.deleteTask(checkNotNull(taskId));

        mCachedTasks.remove(taskId);
    }
}
