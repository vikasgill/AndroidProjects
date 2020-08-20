package com.vikas.mvvmarchexample.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.vikas.mvvmarchexample.viewmodel.ContactViewModel;
import com.vikas.mvvmarchexample.viewmodel.ContactViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import dagger.multibindings.IntoMap;

@Module
@InstallIn(ApplicationComponent.class)
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ContactViewModel.class)
    abstract ViewModel bindRepoViewModel(ContactViewModel repoViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ContactViewModelFactory factory);
}
