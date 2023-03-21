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
import com.squareup.picasso.Picasso;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditDogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditDogFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
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
        Switch switchTv = view.findViewById(R.id.available);

        switchTv.setChecked(isAvailable);

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

        switchTv.setOnClickListener(view1-> {
            Model.instance().updateDogByField(id, "cb", !isAvailable, (unused)->{});
        });

        View button = view.findViewById(R.id.saveUpdatesBtn);
        button.setOnClickListener((view1)-> {
            TextView oldName = view.findViewById(R.id.name);
            String newName = oldName.getText().toString();

            TextView oldDesc = view.findViewById(R.id.description);
            String newDesc = oldDesc.getText().toString();

            TextView oldAge = view.findViewById(R.id.age);
            String nAge = oldAge.getText().toString();
            long newAge = age;
            Boolean isValidAge = oldAge.getText().toString().matches("[0-9]+");

            if (isValidAge) {
                newAge = Long.parseLong(nAge);
            }

            if (name != newName) {
                Model.instance().updateDogByField(id, "name", newName, (used) -> {
                    Model.instance().refreshAllStudents();
                });
            }

            if (description != newDesc) {
                Model.instance().updateDogByField(id, "description", newDesc, (used) -> {
                    Model.instance().refreshAllStudents();
                });
            }

            if (age != newAge) {
                Model.instance().updateDogByField(id, "age", newAge, (used) -> {
                    Model.instance().refreshAllStudents();
                });
            }

        });
        return view;

    }
}