package com.example.class3demo2.model;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface DogDao {
    @Query("select * from Dog")
    LiveData<List<Dog>> getAll();
}
