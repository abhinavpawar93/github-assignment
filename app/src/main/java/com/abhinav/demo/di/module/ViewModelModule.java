package com.abhinav.demo.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.abhinav.demo.viewmodel.GithubViewModel;
import com.abhinav.demo.viewmodel.ViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Alloes us to inject dependencies via constructor injection
 * <p>
 * Author: Abhinav Pawar
 */
@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(GithubViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsGithubModule(GithubViewModel githubViewModel);


    @Binds
    @SuppressWarnings("unused")
    abstract ViewModelProvider.Factory bindsViewModelFactory(ViewModelFactory viewModelFactory);
}
