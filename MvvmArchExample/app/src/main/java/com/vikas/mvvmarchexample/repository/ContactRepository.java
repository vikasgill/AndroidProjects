package com.vikas.mvvmarchexample.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.vikas.mvvmarchexample.model.Contact;
import com.vikas.mvvmarchexample.model.ContactDao;
import com.vikas.mvvmarchexample.model.ContactDatabase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ContactRepository {

    private ContactDao contactDao;
    private LiveData<List<Contact>> allContacts;

    @Inject
    public ContactRepository(Application application){
        ContactDatabase contactDatabase = ContactDatabase.getInstance(application);
        contactDao = contactDatabase.contactDao();
        allContacts = contactDao.getAllContacts();
    }

    public void insertContact(final Contact contact){
        new AsyncTask<Contact, Void, Void>(){

            @Override
            protected Void doInBackground(Contact... contacts) {
                contactDao.insert(contacts[0]);
                return null;
            }
        }.execute(contact);
    }

    public void deleteContact(Contact contact){
        new AsyncTask<Contact, Void, Void>(){

            @Override
            protected Void doInBackground(Contact... contacts) {
                contactDao.delete(contacts[0]);
                return null;
            }
        }.execute(contact);
    }

    public void updateContact(Contact contact){
        new AsyncTask<Contact, Void, Void>(){

            @Override
            protected Void doInBackground(Contact... contacts) {
                contactDao.update(contacts[0]);
                return null;
            }
        }.execute(contact);
    }

    public void deleteAllContacts(){
        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                contactDao.deleteAll();
                return null;
            }
        }.execute();
    }

    public LiveData<List<Contact>> fetchAllContacts() {
        if(allContacts == null) {
            List<Contact> contacts = new ArrayList<>();
            allContacts = new MutableLiveData<>();
        }
        return allContacts;
    }

    public LiveData<List<Contact>> fetchContactsInRange(String startDate, String endDate){
        return contactDao.getContactsBetweenDate(startDate, endDate);
    }
}
