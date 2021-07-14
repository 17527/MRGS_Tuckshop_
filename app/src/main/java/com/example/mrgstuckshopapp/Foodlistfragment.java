package com.example.mrgstuckshopapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;


public class Foodlistfragment extends Fragment implements FoodAdaptor.GetOneFood {

    FirebaseFirestore firebaseFirestore;
    FoodAdaptor mAdaptor;
    RecyclerView recyclerView;
    FoodViewModel viewModel;
    NavController navController;
    ImageButton shopCart;

    public Foodlistfragment() {
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

//setting the ids and the view from the data fetched from firebase through foodmodel and foodviewmodel
        shopCart = view.findViewById(R.id.fab);
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById(R.id.recViewAll);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdaptor = new FoodAdaptor(this);
        navController = Navigation.findNavController(view);
        viewModel = new ViewModelProvider(getActivity()).get(FoodViewModel.class);

        //displays the data throguh foodmodel and foodviewmodel
        viewModel.getFoodList().observe(getViewLifecycleOwner(), new Observer<List<FoodModel>>() {
            @Override
            public void onChanged(List<FoodModel> foodModels) {

                mAdaptor.setFoodModelList(foodModels);
                recyclerView.setAdapter(mAdaptor);

            }
        });

        //when clicked on shop cart, cart fragment opens up
        shopCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_foodlistfragment_to_cartFragment);
            }
        });


    }

// process of items being clicked
    @Override
    public void clickedFood(int position, List<FoodModel> foodModels) {


        //gets the variables of clicked items through foodmodel
        String foodid = foodModels.get(position).getFoodid();
        String description = foodModels.get(position).getDescription();
        String foodname = foodModels.get(position).getFoodname();
        int price = foodModels.get(position).getPrice();
        int quantity = foodModels.get(position).getQuantity();
        String imageURL = foodModels.get(position).getImageURL();
        //this action is set so user is navigated to detail fragment when they click on an item
        FoodlistfragmentDirections.ActionFoodlistfragmentToFoodDescriptionFragment
                action = FoodlistfragmentDirections.actionFoodlistfragmentToFoodDescriptionFragment(quantity);

        //action is the new fooddescription fragment, thus it shows the fetched data in that
        action.setFoodname(foodname);
        action.setDescription(description);
        action.setImageURL(imageURL);
        action.setPrice(price);
        action.setId(foodid);
        action.setQuantity(quantity);

        navController.navigate(action);
    }
}