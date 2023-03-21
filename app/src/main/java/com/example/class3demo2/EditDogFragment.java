package com.example.class3demo2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.class3demo2.databinding.FragmentEditDogBinding;
import com.example.class3demo2.model.Model;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class EditDogFragment extends Fragment {

    private String id;
    private Long age;
    FragmentEditDogBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_dog, container, false);
        binding = FragmentEditDogBinding.inflate(inflater, container, false);
        age = EditDogFragmentArgs.fromBundle(getArguments()).getDogAge();
        String name = EditDogFragmentArgs.fromBundle(getArguments()).getDogName();
        String description = EditDogFragmentArgs.fromBundle(getArguments()).getDogDescription();
        String image = EditDogFragmentArgs.fromBundle(getArguments()).getDogImage();
        id = EditDogFragmentArgs.fromBundle(getArguments()).getDogId();
        boolean isAvailable = EditDogFragmentArgs.fromBundle(getArguments()).getDogCb();

        TextView nameTv = view.findViewById(R.id.name);
        TextView ageTv = view.findViewById(R.id.age);
        ImageView imageTv = view.findViewById(R.id.image);
        TextView descriptionTv = view.findViewById(R.id.description);
        Switch avail = view.findViewById(R.id.available);

        avail.setChecked(isAvailable);
        if (name != null) {
            nameTv.setText(name);
        }
        if (age != null) {
            ageTv.setText(age + "");
        }
        if(image != null) {
            Picasso.get().load(image).into(imageTv);
        }
        if (description != null) {
            descriptionTv.setText(description);
        }

        View button = view.findViewById(R.id.saveUpdatesBtn);
        button.setOnClickListener((view1)-> {
            Boolean isSwitchState = avail.isChecked();

            TextView oldName = view.findViewById(R.id.name);
            String newName = oldName.getText().toString();

            TextView oldDesc = view.findViewById(R.id.description);
            String newDesc = oldDesc.getText().toString();

            TextView oldAge = view.findViewById(R.id.age);
            String nAge = oldAge.getText().toString();
            long newAge = age;
            Boolean isValidAge = oldAge.getText().toString().matches("[0-9]+");

            if(isValidAge) {
                newAge = Long.parseLong(nAge);
                if(newAge >= 31) {
                    newAge = age;
                }
            } else {
                Snackbar.make(view, "Age is not valid", Snackbar.LENGTH_LONG).show();
            }

            if(!name.equals(newName)) {
                Model.instance().updateDogByField(id, "name", newName, (used) -> {
                    Snackbar.make(view, "Name Changed", Snackbar.LENGTH_LONG).show();
                });
            }

            if(!description.equals(newDesc)) {
                Model.instance().updateDogByField(id, "description", newDesc, (used) -> {
                    Snackbar.make(view, "Description Changed", Snackbar.LENGTH_LONG).show();
                });
            }

            if(age != newAge) {
                Model.instance().updateDogByField(id, "age", newAge, (used) -> {
                    Snackbar.make(view, "Age Changed", Snackbar.LENGTH_LONG).show();
                });
            }

            if(isAvailable != isSwitchState) {
                Model.instance().updateDogByField(id, "cb", isSwitchState, (used) -> {
                    Snackbar.make(view, "Check box Changed", Snackbar.LENGTH_LONG).show();
                });
            }
        });

        return view;
    }
}