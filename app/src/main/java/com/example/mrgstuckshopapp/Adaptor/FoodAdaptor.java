package com.example.mrgstuckshopapp.Adaptor;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mrgstuckshopapp.Model.FoodModel;
import com.example.mrgstuckshopapp.R;

import java.util.List;

public class FoodAdaptor extends RecyclerView.Adapter<FoodAdaptor.FoodListHolder>{

// gets the Food Model and makes it into a list
    List<FoodModel> foodModelList;

    //creates the interface
    GetOneFood interfacegetFood;
    public FoodAdaptor(GetOneFood interfacegetFood) {
        this.interfacegetFood = interfacegetFood;
    }

    //inflates the foodliststyle fragment on ViewHolder, Foodlistholder
    @Override
    public FoodListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.foodliststyle, parent, false);
        return new FoodListHolder(view);
    }


    //sets the name, description and imageURL in the foodlist holder
    @Override
    public void onBindViewHolder(FoodAdaptor.FoodListHolder holder, int position) {

        holder.foodname.setText(foodModelList.get(position).getFoodname());
        holder.description.setText(foodModelList.get(position).getDescription());

        Glide.with(holder.itemView.getContext()).load(foodModelList.get(position).getImageURL()).into(holder.imageView);

    }

    //counts the item
    @Override
    public int getItemCount() {
        return foodModelList.size();
    }


// sets the food list from the foodview model
    public void setFoodModelList(List<FoodModel> FoodModelList){
        this.foodModelList = FoodModelList;
    }

    //interface method for each of the food when it is clicked
    public interface GetOneFood{
        void clickedFood(int position, List<FoodModel> foodModels);

    }

    //foodlistholder class helps show these items into recycler view
    class FoodListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView foodname, description;
        ImageView imageView;


        public FoodListHolder(View itemView) {
            super(itemView);

            foodname = itemView.findViewById(R.id.foodname);
            description = itemView.findViewById(R.id.fooddescription);
            imageView = itemView.findViewById(R.id.foodimage);

            foodname.setOnClickListener(this);
            description.setOnClickListener(this);
            imageView.setOnClickListener(this);
        }


        // the method that is implemented when user clicks on food
        @Override
        public void onClick(View v) {
            interfacegetFood.clickedFood(getAdapterPosition(), foodModelList);
        }
    }
}
