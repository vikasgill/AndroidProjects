package com.vikas.mvvmarchexample.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.vikas.mvvmarchexample.repository.ContactRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ContactViewModelFactory implements ViewModelProvider.Factory {

    final ContactRepository mContactRepository;

    @Inject
    public ContactViewModelFactory(ContactRepository contactRepository){
        this.mContactRepository = contactRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ContactViewModel.class)) {
            return (T) new ContactViewModel(mContactRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
