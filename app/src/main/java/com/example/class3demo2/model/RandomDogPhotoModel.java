package com.example.class3demo2.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RandomDogPhotoModel {
    final public static RandomDogPhotoModel instance = new RandomDogPhotoModel();

    final String BASE_URL = "https://dog.ceo/api/";
    Retrofit retrofit;
    RandomDogPhotoApi randomDogPhotoApi;

    private RandomDogPhotoModel() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        randomDogPhotoApi = retrofit.create(RandomDogPhotoApi.class);
    }

    public LiveData<String> getRandomDogPhoto(){
        MutableLiveData<String> data = new MutableLiveData<>();
        Call<RandomDogPhoto> call = randomDogPhotoApi.getRandomDogPhoto();
        call.enqueue(new Callback<RandomDogPhoto>() {
            @Override
            public void onResponse(Call<RandomDogPhoto> call, Response<RandomDogPhoto> response) {
                if (response.isSuccessful()){
                    RandomDogPhoto res = response.body();
                    data.setValue(res.getMessage());
                }else{
                    Log.d("TAG","----- getRandomDogPhoto response error");
                }
            }

            @Override
            public void onFailure(Call<RandomDogPhoto> call, Throwable t) {
                Log.d("TAG","----- getRandomDogPhoto fail");
            }
        });
        return data;
    }


}
