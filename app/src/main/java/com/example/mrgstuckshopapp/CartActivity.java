package com.example.mrgstuckshopapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.mrgstuckshopapp.Adapters.AdaptorForOrder;
import com.example.mrgstuckshopapp.Models.CartModel;
import com.example.mrgstuckshopapp.databinding.ActivityCartBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    ActivityCartBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


//this will fetch the ordered data from database, with helper, and show it

            DbHelper helper = new DbHelper(CartActivity.this);
            ArrayList<CartModel> list = helper.getOrders();

        AdaptorForOrder adapter = new AdaptorForOrder(list, this);
        binding.orderrecyclerview.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.orderrecyclerview.setLayoutManager(layoutManager);

    }



}