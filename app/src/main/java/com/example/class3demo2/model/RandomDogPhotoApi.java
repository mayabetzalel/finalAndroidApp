package com.example.class3demo2.model;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RandomDogPhotoApi {
    @GET("breeds/image/random")
    Call<RandomDogPhoto> getRandomDogPhoto();

}
