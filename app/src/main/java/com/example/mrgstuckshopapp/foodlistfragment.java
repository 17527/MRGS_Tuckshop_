package com.example.mrgstuckshopapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mrgstuckshopapp.Adaptor.FoodAdaptor;
import com.example.mrgstuckshopapp.MVVM.FoodViewModel;
import com.example.mrgstuckshopapp.Model.FoodModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;


public class foodlistfragment extends Fragment {

    FirebaseFirestore firebaseFirestore;
    FoodAdaptor mAdaptor;
    RecyclerView recyclerView;
    FoodViewModel viewModel;

    public foodlistfragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_foodlistfragment, container, false);
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById(R.id.recViewAll);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdaptor = new FoodAdaptor();
        viewModel = new ViewModelProvider(getActivity()).get(FoodViewModel.class);
        viewModel.getFoodList().observe(getViewLifecycleOwner(), new Observer<List<FoodModel>>() {
            @Override
            public void onChanged(List<FoodModel> foodModels) {

                mAdaptor.setFoodModelList(foodModels);
                recyclerView.setAdapter(mAdaptor);

            }
        });


    }



}