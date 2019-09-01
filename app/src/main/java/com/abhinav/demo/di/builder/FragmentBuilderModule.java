package com.abhinav.demo.di.builder;

import com.abhinav.demo.view.fragment.CommentListFragment;
import com.abhinav.demo.view.fragment.IssueListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * This builder provides android injector service to fragments
 * Author: Abhinav Pawar
 */
@Module
public abstract class FragmentBuilderModule {


    @SuppressWarnings("unused")
    @ContributesAndroidInjector
    abstract IssueListFragment issueListFragment();

    @SuppressWarnings("unused")
    @ContributesAndroidInjector
    abstract CommentListFragment commentListFragment();



}
