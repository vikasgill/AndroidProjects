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

    public Contact(){
    }

    public Contact(ContactBuilder builder){
        this.name = builder.name;
        this.number = builder.number;
        if(builder.id != -1)
            this.id = builder.id;
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
        this.createdDate =  createdDate;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public static class ContactBuilder{

        private String name;
        private long number;
        private int id;

        public ContactBuilder(){

        }

        public ContactBuilder setName(String name){
            this.name = name;
            return this;
        }

        public ContactBuilder setNumber(long number){
            this.number = number;
            return this;
        }

        public ContactBuilder setId(int id){
            this.id = id;
            return this;
        }

        public Contact buildContact(){
            return new Contact(this);
        }
    }
}
