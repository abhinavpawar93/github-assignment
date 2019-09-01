package com.abhinav.demo;

import android.app.Activity;
import android.app.Application;

import androidx.work.Worker;

import com.abhinav.demo.di.components.DaggerAppComponent;
import com.abhinav.demo.di.worker.HasWorkerInjector;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * File Description
 * <p>
 * Author: Abhinav Pawar
 */
public class DemoApp extends Application implements HasActivityInjector, HasWorkerInjector {

    @Inject DispatchingAndroidInjector<Worker> workerDispatchingAndroidInjector;


    private static DemoApp sInstance;


    public static DemoApp getAppContext() {
        return sInstance;
    }



    private static synchronized void setInstance(DemoApp app) {
        sInstance = app;
    }
    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeComponent();
        setInstance(this);
    }

    private void initializeComponent() {
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingInjector;
    }

    @Override
    public AndroidInjector<Worker> workerInjector() {
        return workerDispatchingAndroidInjector;

    }
}
