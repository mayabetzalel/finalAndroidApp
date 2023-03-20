package com.example.class3demo2;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.class3demo2.databinding.FragmentAddStudentBinding;
import com.example.class3demo2.model.Dog;
import com.example.class3demo2.model.Model;
import com.example.class3demo2.model.RandomDogPhotoModel;

public class AddDogFragment extends Fragment {
    FragmentAddStudentBinding binding;
    ActivityResultLauncher<Void> cameraLauncher;
    ActivityResultLauncher<String> galleryLauncher;

    Boolean isAvatarSelected = false;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentActivity parentActivity = getActivity();
        parentActivity.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.removeItem(R.id.addDogFragment);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        },this, Lifecycle.State.RESUMED);

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap result) {
                if (result != null) {
                    binding.avatarImg.setImageBitmap(result);
                    isAvatarSelected = true;
                }
            }
        });
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null){
                    binding.avatarImg.setImageURI(result);
                    isAvatarSelected = true;
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddStudentBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        binding.saveBtn.setOnClickListener(view1 -> {
            String name = binding.nameEt.getText().toString();
            String id = binding.idEt.getText().toString();
            Long age = Long.parseLong(binding.ageEt.getText().toString());
            String desc = binding.descriptionEt.getText().toString();
            String createdBy = Model.instance().getLoggedInUser().getEmail();
            String defualtAvatarUrl = "https://firebasestorage.googleapis.com/v0/b/final-project-android-dogs.appspot.com/o/dogs%2Favatar.jpg?alt=media&token=6fb24fe5-a46a-40c2-aae4-9a9674eaba7b";
            Dog dog = new Dog(id, createdBy, name, defualtAvatarUrl, true, age, desc);

            if (isAvatarSelected){
                binding.avatarImg.setDrawingCacheEnabled(true);
                binding.avatarImg.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) binding.avatarImg.getDrawable()).getBitmap();
                Model.instance().uploadImage("images", id, bitmap, url->{
                    if (url != null){
                        dog.setAvatarUrl(url);
                    }
                    Model.instance().addDog(dog, (unused) -> {
                        Navigation.findNavController(view1).popBackStack();
                    });
                });
            } else {
                Model.instance().addDog(dog, (unused) -> {
                    Navigation.findNavController(view1).popBackStack();
                });
            }
        });

        binding.cancellBtn.setOnClickListener(view1 -> Navigation.findNavController(view1).popBackStack(R.id.studentsListFragment,false));

        binding.cameraButton.setOnClickListener(view1->{
            cameraLauncher.launch(null);
        });

        binding.galleryButton.setOnClickListener(view1->{
            galleryLauncher.launch("image/*");
        });
        return view;
    }

}