package com.example.class3demo2.model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.class3demo2.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Student {
    @PrimaryKey
    @NonNull
    public String id = "";

    public String name = "";
    public String avatarUrl = "";
    public Boolean cb = true;
    public Long lastUpdated;
    public String description = "";
    public String createdBy = "";
    public Long age;

    public Student() {
    }

    public Student(String id, String name, String avatarUrl, Boolean cb, String createdBy, String desc, Long age) {
        this.name = name;
        this.id = id;
        this.avatarUrl = avatarUrl;
        this.cb = cb;
        this.createdBy = createdBy;
        this.description = desc;
        this.age = age;
    }

    static final String NAME = "name";
    static final String ID = "id";
    static final String AVATAR = "avatar";
    static final String CB = "cb";
    static final String COLLECTION = "dogs";
    static final String LAST_UPDATED = "lastUpdated";
    static final String LOCAL_LAST_UPDATED = "dogs_local_last_update";

    static final String CREATED_BY = "createdBy";
    static final String DESCRIPTION = "description";
    static final String AGE = "age";


    public static Student fromJson(Map<String, Object> json) {
        String id = (String) json.get(ID);
        String name = (String) json.get(NAME);
        String avatar = (String) json.get(AVATAR);
        Boolean cb = (Boolean) json.get(CB);
        String createdBy = (String) json.get(CREATED_BY);
        String description = (String) json.get(DESCRIPTION);
        Long age = (Long) json.get(AGE);
        Student st = new Student(id, name, avatar, cb, createdBy, description, age);
        try {
            Timestamp time = (Timestamp) json.get(LAST_UPDATED);
            st.setLastUpdated(time.getSeconds());
        } catch (Exception e) {

        }
        return st;
    }

    public static Long getLocalLastUpdate() {
        SharedPreferences sharedPref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        return sharedPref.getLong(LOCAL_LAST_UPDATED, 0);
    }

    public static void setLocalLastUpdate(Long time) {
        SharedPreferences sharedPref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(LOCAL_LAST_UPDATED, time);
        editor.commit();
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put(ID, getId());
        json.put(NAME, getName());
        json.put(AVATAR, getAvatarUrl());
        json.put(CB, getCb());

        json.put(createdBy, getCreatedBy());
        json.put(DESCRIPTION, getDescription());
        json.put(AGE, getAge());

        json.put(LAST_UPDATED, FieldValue.serverTimestamp());
        return json;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setCb(Boolean cb) {
        this.cb = cb;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public Boolean getCb() {
        return cb;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
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

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }
}