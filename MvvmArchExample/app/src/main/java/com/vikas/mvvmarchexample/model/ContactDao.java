package com.vikas.mvvmarchexample.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

@Dao
public interface ContactDao {

    @Insert
    void insert(Contact contact);

    @Delete
    void delete(Contact contact);

    @Update
    void update(Contact contact);

    @Query("delete from contact_table")
    void deleteAll();

    @Query("select * from contact_table order by name ASC")
    LiveData<List<Contact>> getAllContacts();

    @Query("select * from contact_table where date(createdDate) >= date(:startDate) AND date(createdDate) <= date(:endDate) order by name asc")
    LiveData<List<Contact>> getContactsBetweenDate(String startDate, String endDate);
}
