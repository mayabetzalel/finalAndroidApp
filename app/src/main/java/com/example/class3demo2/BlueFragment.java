package com.example.class3demo2;

import android.graphics.Color;
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

public class BlueFragment extends Fragment {
    TextView titleTv;
    ImageView avatarImage;
    TextView idTv;
    String title;

    String id;

    String img;

    public static BlueFragment newInstance(String title, String id, String img ){
        BlueFragment frag = new BlueFragment();
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
        View view = inflater.inflate(R.layout.fragment_blue, container, false);

        title = BlueFragmentArgs.fromBundle(getArguments()).getBlueTitle();
        id = BlueFragmentArgs.fromBundle(getArguments()).getBlueId();
        img = BlueFragmentArgs.fromBundle(getArguments()).getBlueImage();

        TextView titleTv = view.findViewById(R.id.bluefrag_title_tv);
        TextView idTv = view.findViewById(R.id.bluefrag_id_tv);
        ImageView imgTv = view.findViewById(R.id.bluefrag_image_tv);
        if (title != null) {
            titleTv.setText(title);
        }
        if (id != null) {
            idTv.setText(id);
        }
        if(img != null) {
            Picasso.get().load(img).into(imgTv);
        }

        View button = view.findViewById(R.id.bluefrag_back_btn);
        button.setOnClickListener((view1)->{
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