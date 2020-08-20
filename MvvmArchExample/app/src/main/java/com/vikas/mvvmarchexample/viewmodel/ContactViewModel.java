package com.vikas.mvvmarchexample.viewmodel;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.vikas.mvvmarchexample.model.Contact;
import com.vikas.mvvmarchexample.repository.ContactRepository;
import java.util.List;

import javax.inject.Inject;

public class ContactViewModel extends ViewModel {

    private ContactRepository mContactRepository;
    private LiveData<List<Contact>> mAllContacts;
    private LiveData<List<Contact>> mContactsInRange;

    @Inject
    public ContactViewModel(ContactRepository contactRepository) {
        mContactRepository = contactRepository;
        mAllContacts = mContactRepository.fetchAllContacts();
    }

    public void insert(Contact contact){
        mContactRepository.insertContact(contact);
    }

    public void delete(Contact contact){
        mContactRepository.deleteContact(contact);
    }

    public void update(Contact contact){
        mContactRepository.updateContact(contact);
    }

    public LiveData<List<Contact>> getAllContacts() {
        return mAllContacts;
    }

    public LiveData<List<Contact>> fetchMatchingContacts(String startDate, String endDate){
        return mContactRepository.fetchContactsInRange(startDate, endDate);
    }
}
