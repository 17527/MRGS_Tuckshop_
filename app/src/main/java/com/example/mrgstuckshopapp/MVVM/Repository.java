package com.example.mrgstuckshopapp.MVVM;


import com.example.mrgstuckshopapp.Model.FoodModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Repository {

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    List<FoodModel> foodModelList = new ArrayList<>();

    FoodList interfaceoffoodlist;

    public Repository(FoodList interfaceoffoodlist) {
        this.interfaceoffoodlist = interfaceoffoodlist;
    }

    public void getFood(){
        firebaseFirestore.collection("Food").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(Task<QuerySnapshot> task) {


                if (task.isSuccessful()){
                    foodModelList.clear();

                    for (DocumentSnapshot ds: Objects.requireNonNull(task.getResult()).getDocuments()){
                        FoodModel foodModel =new FoodModel();
                        foodModel.setFoodid(ds.getId());
                        foodModel.setDescription(ds.get("description").toString());
                        foodModel.setPrice(Integer.parseInt(ds.get("price").toString()));
                        foodModel.setImageURL(ds.get("imageURL").toString());
                        foodModel.setFoodname(ds.get("foodname").toString());
                        foodModel.setQuantity(Integer.parseInt(ds.get("quantity").toString()));
                        foodModelList.add(foodModel);
                        interfaceoffoodlist.foodLists(foodModelList);



                    }
                }


            }
        });
    }
//    public void cartfoodid(){
//        firebaseFirestore.collection("Cart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(Task<QuerySnapshot> task) {
//
//
//                if (task.isSuccessful()) {
//
//                    for (DocumentSnapshot ds : Objects.requireNonNull(task.getResult()).getDocuments()) {
//                        FoodModel foodModel = new FoodModel();
//                        foodModel.setCartfoodid(ds.getId());
//                    }
//                }
//            }
//        });
//    }


    public interface FoodList{
        void foodLists(List<FoodModel> foodModels);
    }
}
