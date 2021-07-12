package com.example.mrgstuckshopapp.MVVM;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mrgstuckshopapp.Model.FoodModel;

import java.util.List;

public class FoodViewModel extends ViewModel implements Repository.FoodList {

    //this mutable live data serves a great purpose, this helps the information to be updated straight away from the firebase
    //so as soon as the infomration is updated on firebase, the information on app is also updated, and vice versa.
    MutableLiveData<List<FoodModel>> mutableLiveData = new MutableLiveData<List<FoodModel>>();
    Repository repository = new Repository(this);

    public FoodViewModel() {
        repository.getFood();
    }


    public LiveData<List<FoodModel>> getFoodList(){
        return mutableLiveData;
    }

    @Override
    public void foodLists(List<FoodModel> foodModels){
        mutableLiveData.setValue(foodModels);
    }

}
