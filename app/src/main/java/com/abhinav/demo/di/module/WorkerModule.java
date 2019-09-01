package com.abhinav.demo.di.module;

import androidx.work.Worker;

import com.abhinav.demo.background.workmanager.DailySyncWorker;
import com.abhinav.demo.di.worker.SyncWorkerModule;
import com.abhinav.demo.di.worker.WorkerKey;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module(subcomponents = {
        SyncWorkerModule.class})
public abstract class WorkerModule {

    @Binds
    @IntoMap
    @WorkerKey(DailySyncWorker.class)
    abstract AndroidInjector.Factory<? extends Worker> bindDailyWorkerrFactory(SyncWorkerModule.Builder syncWorker);
}