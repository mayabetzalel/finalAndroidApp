package com.example.class3demo2.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Model {
    private static final Model _instance = new Model();

    private Executor executor = Executors.newSingleThreadExecutor();
    private Handler mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    private FirebaseModel firebaseModel = new FirebaseModel();
    AppLocalDbRepository localDb = AppLocalDb.getAppDb();

    public static Model instance(){
        return _instance;
    }
    private Model(){
    }

    public interface Listener<T>{
        void onComplete(T data);
    }


    public enum LoadingState{
        LOADING,
        NOT_LOADING
    }
    final public MutableLiveData<LoadingState> EventStudentsListLoadingState = new MutableLiveData<LoadingState>(LoadingState.NOT_LOADING);


    private LiveData<List<Student>> studentList;
    public LiveData<List<Student>> getAllStudents() {
        if(studentList == null){
            studentList = localDb.studentDao().getAll();
            refreshAllStudents();
        }
        return studentList;
    }

    public void refreshAllStudents(){
        EventStudentsListLoadingState.setValue(LoadingState.LOADING);
        // get local last update
        Long localLastUpdate = Student.getLocalLastUpdate();
        // get all updated recorde from firebase since local last update
        firebaseModel.getAllStudentsSince(localLastUpdate,list->{
            executor.execute(()->{
                Log.d("TAG", " firebase return : " + list.size());
                Long time = localLastUpdate;
                for(Student st:list){
                    // insert new records into ROOM
                    localDb.studentDao().insertAll(st);
                    if (time < st.getLastUpdated()){
                        time = st.getLastUpdated();
                    }
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // update local last update
                Student.setLocalLastUpdate(time);
                EventStudentsListLoadingState.postValue(LoadingState.NOT_LOADING);
            });
        });
    }

    public void addStudent(Student st, Listener<Void> listener){
        firebaseModel.addStudent(st,(Void)->{
            refreshAllStudents();
            listener.onComplete(null);
        });
    }

    public void addDog(Dog dog, Listener<Void> listener){
        firebaseModel.addDog(dog,(Void)->{
            refreshAllStudents();
            listener.onComplete(null);
        });
    }

    public void uploadImage(String folder, String name, Bitmap bitmap,Listener<String> listener) {
        firebaseModel.uploadImage(folder, name, bitmap,listener);
    }

    public void registerUser(String name, String email, String password, ImageView img) {
        firebaseModel.registerUser(name, email, password, img);
    }

    public void loginUser(String email, String password, FirebaseModel.OnLoginCompleteListener listener) {
        firebaseModel.loginUser(email, password, listener);
    }

    public User getLoggedInUser() {
        return firebaseModel.getLoggedInUser();
    }

    public void updateUserName(String name) {
        firebaseModel.updateUserName(name);
    }

    public void updateUserPhoto(Uri photo) {
        firebaseModel.updateUserPhoto(photo);
    }

    public void logout() {
        firebaseModel.logout();
    }

}
