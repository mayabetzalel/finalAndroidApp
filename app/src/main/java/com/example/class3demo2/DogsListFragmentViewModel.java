package com.example.class3demo2;

import com.example.class3demo2.model.Model;
import com.example.class3demo2.model.Student;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class DogsListFragmentViewModel extends ViewModel {
    private LiveData<List<Student>> data = Model.instance().getAllStudents();;

    private LiveData<List<Student>> dataByUser = Model.instance().getAllDogsByUser();
    LiveData<List<Student>> getData(){

        return data;
    }
    LiveData<List<Student>> getDataByUser() {
        return dataByUser;
    }
}
