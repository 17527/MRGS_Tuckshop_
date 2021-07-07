package com.example.mrgstuckshopapp;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mrgstuckshopapp.Adaptor.FoodAdaptor;
import com.example.mrgstuckshopapp.MVVM.FoodViewModel;
import com.example.mrgstuckshopapp.Model.FoodModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;


public class HomeFragment extends Fragment {

    FirebaseFirestore firebaseFirestore;
    FoodAdaptor mAdaptor;
    RecyclerView recyclerView;
    FoodViewModel viewModel;
    NavController navController;
    FloatingActionButton shopCart;

    public HomeFragment() {
        // Required empty public constructor
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        shopCart = view.findViewById(R.id.fab);
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById(R.id.recViewAll);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdaptor = new FoodAdaptor((FoodAdaptor.GetOneFood) this);
        navController = Navigation.findNavController(view);
        viewModel = new ViewModelProvider(getActivity()).get(FoodViewModel.class);
        viewModel.getFoodList().observe(getViewLifecycleOwner(), new Observer<List<FoodModel>>() {
            @Override
            public void onChanged(List<FoodModel> foodModels) {

                mAdaptor.setFoodModelList(foodModels);
                recyclerView.setAdapter(mAdaptor);

            }
        });

        shopCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_foodlistfragment_to_cartFragment);
            }
        });


    }

    // helps identify which item the user has clicked on

    public void clickedFood(int position, List<FoodModel> foodModels) {

        String foodid = foodModels.get(position).getFoodid();
        String description = foodModels.get(position).getDescription();
        String foodname = foodModels.get(position).getFoodname();
        int price = foodModels.get(position).getPrice();
        int quantity = foodModels.get(position).getQuantity();
        String imageURL = foodModels.get(position).getImageURL();
        //this action is set so user is navigated to detail fragment when they click on an item
        FoodlistfragmentDirections.ActionFoodlistfragmentToFoodDescriptionFragment
                action = FoodlistfragmentDirections.actionFoodlistfragmentToFoodDescriptionFragment(quantity);

        action.setFoodname(foodname);
        action.setDescription(description);
        action.setImageURL(imageURL);
        action.setPrice(price);
        action.setId(foodid);
        action.setQuantity(quantity);

        navController.navigate(action);
    }
}