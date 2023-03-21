package com.example.class3demo2.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.class3demo2.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

public class Dog {
    @PrimaryKey
    @NonNull
    public String id="";

    public String name="";
    public long age = 0;
    public String avatarUrl="";
    public String description="";
    public String createdBy = "";
    public Boolean cb = true;
    public Long lastUpdated;

    public Dog(){
    }
    public Dog(String id, String createdBy, String name, String avatarUrl, Boolean cb, long age, String desc) {
        this.id = id;
        this.name = name;
        this.createdBy = createdBy;
        this.avatarUrl = avatarUrl;
        this.description = desc;
        this.age = age;
        this.cb = cb;
    }

    static final String NAME = "name";
    static final String DESCRIPTION = "description";
    static final String AVATAR = "avatar";
    static final String AGE = "age";

    static final String CB = "cb";
    static final String COLLECTION = "dogs";
    static final String CREATED_BY = "createdBy";
    static final String ID = "id";
    static final String LAST_UPDATED = "lastUpdated";
    static final String LOCAL_LAST_UPDATED = "dogs_local_last_update";
    public static Dog fromJson(Map<String,Object> json){
        String description = (String)json.get(DESCRIPTION);
        String id = (String)json.get(ID);
        String name = (String)json.get(NAME);
        String avatar = (String)json.get(AVATAR);
        String createdBy = (String)json.get(CREATED_BY);
        Boolean cb = (Boolean) json.get(CB);
        Long age = (long) json.get(AGE);
        Dog dog = new Dog(id, createdBy, name, avatar, cb, age, description);
        try {
            Timestamp time = (Timestamp) json.get(LAST_UPDATED);
            dog.setLastUpdated(time.getSeconds());
        } catch (Exception e) {

        }
        return dog;
    }


    public Map<String,Object> toJson(){
        Map<String, Object> json = new HashMap<>();
        json.put(ID, getId());
        json.put(NAME, getName());
        json.put(AVATAR, getAvatarUrl());
        json.put(DESCRIPTION, getDescription());
        json.put(AGE, getAge());
        json.put(CREATED_BY, getCreatedBy());
        json.put(CB, getCb());
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());
        return json;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }
    public Boolean getCb() {
        return cb;
    }
    public void setCb(Boolean cb) {
        this.cb = cb;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }
    @NonNull
    public String getId() {
        return id;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    public String getName() {
        return name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
