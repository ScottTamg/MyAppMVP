package com.demo.myappmvp.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Objects;
import java.util.UUID;

/**
 * Created by Administrator on 2016/12/6.
 */

public final class Task {
    @NonNull
    private final String mId;
    @Nullable
    private final String mTitle;
    @Nullable
    private final String mDescription;

    private final boolean mCompleted;

    public Task(@NonNull String id, @Nullable String title, @Nullable String description, boolean completed) {
        this.mId = id;
        this.mTitle = title;
        this.mDescription = description;
        this.mCompleted = completed;
    }

    public Task(@Nullable String title, @Nullable String description, boolean completed) {
        this(UUID.randomUUID().toString(), title, description, completed);
    }

    public Task(@Nullable String title, @Nullable String description) {
        this(UUID.randomUUID().toString(), title, description, false);
    }

    public Task(@NonNull String id, @Nullable String title, @Nullable String description) {
        this(id, title, description, false);
    }

    @NonNull
    public String getId() {
        return mId;
    }

    @Nullable
    public String getTitle() {
        return mTitle;
    }

    @Nullable
    public String getDescription() {
        return mDescription;
    }

    public boolean isCompleted() {
        return mCompleted;
    }

    @Nullable
    public String getTitleForList() {
        if (!mTitle.isEmpty()) {
            return mTitle;
        } else {
            return mDescription;
        }
    }

    public boolean isActive() {
        return !mCompleted;
    }

    public boolean isEmpty() {
        return mTitle.isEmpty() && mDescription.isEmpty();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Task task = (Task) obj;

        return Objects.equals(mId, task.mId) &&
                Objects.equals(mTitle, task.mTitle) &&
                Objects.equals(mDescription, task.getDescription());
    }

//    @Override
//    public int hashCode() {
//        return Objects.hashCode(mId, mTitle, mDescription);
//    }

    @Override
    public String toString() {
        return "Task with title " + mTitle;
    }
}
