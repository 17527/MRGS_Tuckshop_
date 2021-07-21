package com.example.mrgstuckshopapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mrgstuckshopapp.Adaptor.FoodAdaptor;
import com.example.mrgstuckshopapp.Model.FoodModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class CartFragment extends Fragment  implements FoodAdaptor.GetOneFood {

    //setting the variables
    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    FoodAdaptor mAdaptor;
    double orderPrice=0;
    Button confirmorder;
    AlertDialog.Builder confirm_popup, placed_order_popup;
    LayoutInflater inflater;
    TextView totalcartprice;
    FirebaseAuth fAuth;
    String useremail;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);

    }

    public void setTotalcartprice(TextView totalprice) {
        this.totalcartprice = totalprice;
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //setting ids and the layout
        confirmorder = view.findViewById(R.id.confirm_button);
        totalcartprice = view.findViewById(R.id.totalcartprice);
        fAuth = FirebaseAuth.getInstance();

        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById(R.id.recViewAll);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdaptor = new FoodAdaptor(this);

        //calling the cartorder process which is done below
        getCartOrder();





//when clicked on confirm, it opens alert dialog where it shows a note about what happens, if
// the food is not collected. if they click on confirm then it says 'order confirmed for (user's email)
//if they cancel then the alert dialog closes

        confirmorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())

                        .setTitle("Confirm Order?")
                        //confirm button will delete the files from cart and show how they can collect order
                        //then they are navigated back to order page
                        .setMessage("Note: If Order is not picked up,\n" +
                                "your school account will be charged with\n" +
                                "the amount due plus the 25% extra penalty")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //To delete all documents from Cart
                                firebaseFirestore.collection("Cart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (DocumentSnapshot ds : Objects.requireNonNull(task.getResult()).getDocuments()) {
                                                ds.getReference().delete();
                                            }
                                        }
                                    }

                                });

                                useremail = String.valueOf(fAuth.getCurrentUser().getEmail());
                                Toast.makeText(getContext(), "Your Order has been placed for " + useremail, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), HomePage.class);
                                startActivity(intent);

                            }
                        }).setNegativeButton("Cancel", null)
                        .show();

            }
        });

    }


//method of getting orders from cart collection in firebase and displaying it
//sets the foodmodel into an array list, then attempts to get the cart collection
//if this task is successful, it gets all the variables required and hooks it with foodmodel variables
//then using foodadaptor and recycler view, it displays this information
   void getCartOrder()
    {

        List<FoodModel> foodModels = new ArrayList<>();

        firebaseFirestore.collection("Cart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(Task<QuerySnapshot> task) {


                if (task.isSuccessful()){

                    for (DocumentSnapshot ds: Objects.requireNonNull(task.getResult()).getDocuments()){
                        FoodModel foodModel =new FoodModel();
                        foodModel.setFoodid(ds.getId());
                        double totalPrice = Integer.parseInt(ds.get("price").toString()) * Integer.parseInt(ds.get("quantity").toString());
                        orderPrice = orderPrice + totalPrice;
                        foodModel.setDescription("Quantity : "+Integer.parseInt(ds.get("quantity").toString()) + "      Price : $"+totalPrice +"0");
                        foodModel.setImageURL(ds.get("imageURL").toString());
                        foodModel.setFoodname(ds.get("foodname").toString());
                        foodModels.add(foodModel);



                    }
                    mAdaptor.setFoodModelList(foodModels);
                    recyclerView.setAdapter(mAdaptor);
                    totalcartprice.setText("Total Price: $"+orderPrice+ "0");
                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Log.d("FinalCartFetchException",e.toString());
            }
        });

    }

    @Override
    public void clickedFood(int position, List<FoodModel> foodModels) {

    }

}