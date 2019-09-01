package com.abhinav.demo.di.components;

import android.app.Application;

import com.abhinav.demo.DemoApp;
import com.abhinav.demo.di.builder.ActivityBuilderModule;
import com.abhinav.demo.di.module.AndroidWorkerInjectionModule;
import com.abhinav.demo.di.module.AppModule;
import com.abhinav.demo.di.module.WorkerModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * The main application component which initializes all the dependent modules
 * Author: Abhinav Pawar
 */
@Singleton
@Component(modules = {
        AppModule.class,
        AndroidInjectionModule.class,
        AndroidWorkerInjectionModule.class,
        WorkerModule.class,
        ActivityBuilderModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(DemoApp DemoApp);
}