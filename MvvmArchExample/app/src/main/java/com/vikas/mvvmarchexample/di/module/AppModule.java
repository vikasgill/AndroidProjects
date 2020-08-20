package com.vikas.mvvmarchexample.di.module;

import android.app.Application;

import androidx.lifecycle.ViewModelProvider;

import com.vikas.mvvmarchexample.repository.ContactRepository;
import com.vikas.mvvmarchexample.viewmodel.ContactViewModel;
import com.vikas.mvvmarchexample.viewmodel.ContactViewModelFactory;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public class AppModule {


    @Singleton
    @Provides
    public static ContactRepository provideRepository(Application application){
        return new ContactRepository(application);
    }

    @Singleton
    @Provides
    static ViewModelProvider.Factory provideViewModelFactory(ContactRepository contactRepository){
        return new ContactViewModelFactory(contactRepository);
    }
}
