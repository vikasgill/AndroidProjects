package com.vikas.mvvmarchexample.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.vikas.mvvmarchexample.utils.DateTimeConverter;

import java.util.Calendar;
import java.util.Date;

@Entity(tableName = "contact_table")
public class Contact {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private long number;

    @TypeConverters(DateTimeConverter.class)
    private Date createdDate;

    public Contact(String name, long number) {
        this.name = name;
        this.number = number;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getNumber() {
        return number;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
