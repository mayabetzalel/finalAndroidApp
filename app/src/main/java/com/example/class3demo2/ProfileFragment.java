package com.example.class3demo2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.class3demo2.databinding.FragmentProfileBinding;
import com.example.class3demo2.model.Model;
import com.example.class3demo2.model.User;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;


public class ProfileFragment extends Fragment {

    private Boolean validateProfile(View view) {

        EditText nameEt = view.findViewById(R.id.profile_name_et);
        String name = nameEt.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Snackbar.make(binding.getRoot(), "Please enter your name", Snackbar.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    FragmentProfileBinding binding;
    ActivityResultLauncher<Void> cameraLauncher;
    ActivityResultLauncher<String> galleryLauncher;
    Boolean isAvatarSelected = false;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap result) {
                if (result != null) {
                    binding.editprofileAvatarImg.setImageBitmap(result);
                    isAvatarSelected = true;
                }
            }
        });
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null) {
                    binding.editprofileAvatarImg.setImageURI(result);
                    isAvatarSelected = true;
                }
            }
        });
    }

    public void setUpData(View view) {
        User loggedInUser = Model.instance().getLoggedInUser();

        ImageView avatarImage = view.findViewById(R.id.editprofile_avatar_img);

        binding.profileNameEt.setText(loggedInUser.getName());

        if (loggedInUser.getPhotoURL() != null) {
            Picasso.get().load(loggedInUser.getPhotoURL()).placeholder(R.drawable.avatar).into(avatarImage);
        } else {
            avatarImage.setImageResource(R.drawable.avatar);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = binding.getRoot();
        setUpData(view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        setUpData(view);

        binding.profileLogoutBtn.setOnClickListener(view2 -> {
            Model.instance().logout();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        });

        binding.profileSaveBtn.setOnClickListener(view1 -> {

            if (validateProfile(binding.getRoot())) {
                String name = binding.profileNameEt.getText().toString();

                Model.instance().updateUserName(name);

                User loggedInUser = Model.instance().getLoggedInUser();

                ImageView avatarImage = view.findViewById(R.id.editprofile_avatar_img);

                if (isAvatarSelected) {
                    String id = loggedInUser.getEmail();
                    String FOLDER_NAME = "profileAvatars";

                    avatarImage.setDrawingCacheEnabled(true);
                    avatarImage.buildDrawingCache();
                    Bitmap bitmap = ((BitmapDrawable) avatarImage.getDrawable()).getBitmap();
                    Model.instance().uploadImage(FOLDER_NAME, id, bitmap, url -> {
                        if (url != null) {
                            Uri photoUri = Uri.parse(url);
                            Model.instance().updateUserPhoto(photoUri);
                        }
                    });
                }

                Snackbar.make(view, "Profile information updated", Snackbar.LENGTH_LONG).show();
            }

        });

        binding.profileCancelBtn.setOnClickListener(view1 -> {
            setUpData(view);
        });

        binding.cameraButton.setOnClickListener(view1 -> {
            cameraLauncher.launch(null);
        });

        binding.galleryButton.setOnClickListener(view1 -> {
            galleryLauncher.launch("image/*");
        });
        return view;
    }

}