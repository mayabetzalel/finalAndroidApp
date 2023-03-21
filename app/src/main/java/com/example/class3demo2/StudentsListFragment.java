package com.example.class3demo2;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.class3demo2.databinding.FragmentDogsListBinding;
import com.example.class3demo2.model.Model;
import com.example.class3demo2.model.Student;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

public class StudentsListFragment extends Fragment {
    FragmentDogsListBinding binding;
    DogRecyclerAdapter adapter;
    DogsListFragmentViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDogsListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new DogRecyclerAdapter(getLayoutInflater(), viewModel.getData().getValue(), false);
        binding.recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new DogRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Log.d("TAG", "Row was clicked " + pos);
                Log.d("TAG", "Row " + viewModel.getData().getValue());
                Student st = viewModel.getData().getValue().get(pos);
                StudentsListFragmentDirections.ActionStudentsListFragmentToBlueFragment action = StudentsListFragmentDirections.actionStudentsListFragmentToBlueFragment(st.name, st.id, st.avatarUrl, st.description, st.age, st.createdBy);
                Navigation.findNavController(view).navigate(action);
            }
        });

        View addButton = view.findViewById(R.id.btnAdd);
        NavDirections action = StudentsListFragmentDirections.actionGlobalAddStudentFragment();
        addButton.setOnClickListener(Navigation.createNavigateOnClickListener(action));

        binding.progressBar.setVisibility(View.GONE);

        viewModel.getData().observe(getViewLifecycleOwner(),list->{
            adapter.setData(list);
        });

        Model.instance().EventStudentsListLoadingState.observe(getViewLifecycleOwner(),status->{
            binding.swipeRefresh.setRefreshing(status == Model.LoadingState.LOADING);
        });

        binding.swipeRefresh.setOnRefreshListener(()->{
            reloadData();
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(DogsListFragmentViewModel.class);
    }

    void reloadData(){
//        binding.progressBar.setVisibility(View.VISIBLE);
        Model.instance().refreshAllStudents();
    }
}