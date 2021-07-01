package com.example.mrgstuckshopapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.mrgstuckshopapp.Adapters.OrderAdapter;
import com.example.mrgstuckshopapp.Models.OrderModel;
import com.example.mrgstuckshopapp.databinding.ActivityOrderPageBinding;

import java.util.ArrayList;

public class OrderPage extends AppCompatActivity {
    Toolbar toolbar;
    ActivityOrderPageBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar = findViewById(R.id.toolbar);
        binding = ActivityOrderPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(toolbar);

        ArrayList<OrderModel> list = new ArrayList<>();

        list.add(new OrderModel(R.drawable.garlic_bread, "Garlic Bread", "3", "A bun/bread consisting of Garlic Butter"));
        list.add(new OrderModel(R.drawable.butterchknrice, "Butter Chicken Rice", "5", "Sweet Indian Chicken Curry with rice"));
        list.add(new OrderModel(R.drawable.hashbrown, "Hashbrown", "1", "Boiled, shredded potatoes fried until golden color"));
        list.add(new OrderModel(R.drawable.hotdog, "Hot Dog", "4", "Cooked sausage served in a bun, with ketchup"));
        list.add(new OrderModel(R.drawable.noodles, "Noodles", "5", "Lorem ipsum dolor sit amet, consectetur adipiscing elit"));
        list.add(new OrderModel(R.drawable.wedges, "Wedges", "3", "Wedge-shaped irregular potato slices fried in oil"));

        OrderAdapter adapter = new OrderAdapter(list, this);
        binding.recyclerview.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclerview.setLayoutManager(layoutManager);
    }
}