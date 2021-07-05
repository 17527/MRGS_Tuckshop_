package com.example.mrgstuckshopapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.mrgstuckshopapp.Model.FoodModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class FoodDescriptionFragment extends Fragment {

    NavController navController;
    int quantity = 0;
    FirebaseFirestore firebaseFirestore;
    Button add, sub, order;
    TextView foodname, description, quantityview, orderINFO;
    ImageView imageView;
    String foodid;
    String name;
    String fooddescription;
    String imageURL;
    int price = 0;
    int totalPrice = 0;

    public FoodDescriptionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_desrciption, container, false);
    }

    //setting the values
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView = view.findViewById(R.id.fooddetailimage);
        foodname = view.findViewById(R.id.fooddetailname);
        description = view.findViewById(R.id.fooddetaildetail);
        add = view.findViewById(R.id.upquantity);
        sub = view.findViewById(R.id.downquantity);
        quantityview = view.findViewById(R.id.fooddetailquantity);
        firebaseFirestore = FirebaseFirestore.getInstance();
        navController = Navigation.findNavController(view);
        order = view.findViewById(R.id.addtocartorderdetail);
        orderINFO = view.findViewById(R.id.orderINFO);

        name = FoodDescriptionFragmentArgs.fromBundle(getArguments()).getFoodname();
        foodid = FoodDescriptionFragmentArgs.fromBundle(getArguments()).getId();
        imageURL = FoodDescriptionFragmentArgs.fromBundle(getArguments()).getImageURL();
        price = FoodDescriptionFragmentArgs.fromBundle(getArguments()).getPrice();
        fooddescription = FoodDescriptionFragmentArgs.fromBundle(getArguments()).getDescription();
        foodid = FoodDescriptionFragmentArgs.fromBundle(getArguments()).getId();

        Glide.with(view.getContext()).load(imageURL).into(imageView);
        foodname.setText(name + "$ " + String.valueOf(price));
        description.setText(fooddescription);

        //fetching the recent quantity from firestore and displaying it
        firebaseFirestore.collection("Food").document(foodid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot value, FirebaseFirestoreException error) {

                FoodModel foodModel = value.toObject(FoodModel.class);
                quantity = foodModel.getQuantity();
                quantityview.setText(String.valueOf(quantity));
            }
        });

// add button increases the quantity
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (quantity == 10){
                    Toast.makeText(getContext(), "Nothing in Cart", Toast.LENGTH_SHORT).show();
                    quantityview.setText(String.valueOf(quantity));

                }else{

                    quantity++;
                    quantityview.setText(String.valueOf(quantity));

                    //showing the price
                    totalPrice = quantity*price;
                    orderINFO.setText(String.valueOf("$ " + totalPrice));

                    //updating the quantity on firebase
                    firebaseFirestore.collection("Food").document(foodid).update("quantity", quantity).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task) {

                        }
                    });

                }


            }
        });
        // sub button decreases the quantity
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (quantity == 0){
                    Toast.makeText(getContext(), "Nothing in Cart", Toast.LENGTH_SHORT).show();
                    quantityview.setText(String.valueOf(quantity));

                }else{

                    quantity--;
                    quantityview.setText(String.valueOf(quantity));

                    //showing the price
                    totalPrice = quantity*price;
                    orderINFO.setText(String.valueOf("$ " + totalPrice));

                    //updating quantity in firebase
                    firebaseFirestore.collection("Food").document(foodid).update("quantity", quantity).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task) {

                        }
                    });

                }


            }
        });

    }
}