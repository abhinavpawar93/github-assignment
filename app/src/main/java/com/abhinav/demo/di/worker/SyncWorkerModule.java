package com.abhinav.demo.di.worker;

import com.abhinav.demo.background.workmanager.DailySyncWorker;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent
public interface SyncWorkerModule extends AndroidInjector<DailySyncWorker> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<DailySyncWorker>{}
}