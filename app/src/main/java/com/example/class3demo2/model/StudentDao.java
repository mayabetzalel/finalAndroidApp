package com.example.class3demo2.model;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface StudentDao {
    @Query("select * from Student")
    LiveData<List<Student>> getAll();

    @Query("select * from Student where id = :studentId")
    Student getStudentById(String studentId);

    @Query("select * from Student where createdBy= :createdBy")
    LiveData<List<Student>> getAllByUser(String createdBy);

    @Query("delete from Student")
    void update();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Student... students);

    @Delete
    void delete(Student student);
}

