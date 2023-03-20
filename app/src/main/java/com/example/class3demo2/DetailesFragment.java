package com.example.class3demo2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class DetailesFragment extends Fragment {
    TextView titleTv;
    ImageView avatarImage;
    TextView idTv;
    String title;
    String id;
    String img;
    String description;
    Long age;

    public static DetailesFragment newInstance(String title, String id, String img ){
        DetailesFragment frag = new DetailesFragment();
        Bundle bundle = new Bundle();
        bundle.putString("TITLE",title);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null){
            this.title = bundle.getString("TITLE");
            this.id = bundle.getString("ID");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detailes, container, false);

        title = DetailesFragmentArgs.fromBundle(getArguments()).getBlueTitle();
        id = DetailesFragmentArgs.fromBundle(getArguments()).getBlueId();
        img = DetailesFragmentArgs.fromBundle(getArguments()).getBlueImage();
        description = DetailesFragmentArgs.fromBundle(getArguments()).getDescription();
        age = DetailesFragmentArgs.fromBundle(getArguments()).getAge();

//        TextView titleTv = view.findViewById(R.id.description);
        TextView idTv = view.findViewById(R.id.name);
        ImageView imgTv = view.findViewById(R.id.image);
        TextView ageTv = view.findViewById(R.id.age);
        TextView descriptionTv = view.findViewById(R.id.description);

        if (description != null) {
            descriptionTv.setText(description);
        }
        if (id != null) {
            idTv.setText(id);
        }
        if (age != null) {
            ageTv.setText(age + " years old");
        }
        if(img != null) {
            Picasso.get().load(img).into(imgTv);
        }

        View button = view.findViewById(R.id.bluefrag_back_btn);
        button.setOnClickListener((view1)-> {
            Navigation.findNavController(view1).popBackStack();
        });
        return view;
    }

    public void setTitle(String title) {
        this.title = title;
        if (titleTv != null){
            titleTv.setText(title);
        }
    }
}