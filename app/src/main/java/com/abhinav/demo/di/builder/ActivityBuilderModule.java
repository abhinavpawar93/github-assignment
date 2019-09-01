package com.abhinav.demo.di.builder;

import com.abhinav.demo.view.activity.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * The module which provides the android injection service to Activities.
 * Author: Abhinav Pawar
 */
@Module
public abstract class ActivityBuilderModule {

    @SuppressWarnings("unused")
    @ContributesAndroidInjector(modules = FragmentBuilderModule.class)
    abstract MainActivity mainActivity();


}
