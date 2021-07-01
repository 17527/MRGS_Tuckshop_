package com.example.mrgstuckshopapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.mrgstuckshopapp.Adapters.AdaptorForOrder;
import com.example.mrgstuckshopapp.Adapters.OrderAdapter;
import com.example.mrgstuckshopapp.Models.ModelforOrder;
import com.example.mrgstuckshopapp.databinding.ActivityCartBinding;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    ActivityCartBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayList<ModelforOrder> list = new ArrayList<>();
        list.add(new ModelforOrder(R.drawable.garlic_bread, "Garlic Bread", "3", "123456"));
        list.add(new ModelforOrder(R.drawable.butterchknrice, "Butter Chicken Rice", "5", "234561"));
        list.add(new ModelforOrder(R.drawable.hashbrown, "Hashbrown", "1", "345612"));
        list.add(new ModelforOrder(R.drawable.hotdog, "Hot Dog", "4", "456123"));
        list.add(new ModelforOrder(R.drawable.noodles, "Noodles", "5", "561234"));
        list.add(new ModelforOrder(R.drawable.wedges, "Wedges", "3", "612345"));

        AdaptorForOrder adapter = new AdaptorForOrder(list, this);
        binding.orderrecyclerview.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.orderrecyclerview.setLayoutManager(layoutManager);

    }
}