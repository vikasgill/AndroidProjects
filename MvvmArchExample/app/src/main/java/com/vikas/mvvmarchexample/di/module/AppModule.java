package com.vikas.mvvmarchexample.di.module;

import android.app.Application;

import com.vikas.mvvmarchexample.repository.ContactRepository;

import javax.inject.Singleton;

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
}
