package com.example.class3demo2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.class3demo2.model.Model;
import com.example.class3demo2.model.User;


public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        User loggedInUser = Model.instance().getLoggedInUser();
        Log.d("lotan", "In profile");
        Log.d("lotan", "Email: " + loggedInUser.getEmail());
        Log.d("lotan", "Name: " + loggedInUser.getName());
        Log.d("lotan", "Photo: " + loggedInUser.getPhotoURL());
        return view;
    }
}