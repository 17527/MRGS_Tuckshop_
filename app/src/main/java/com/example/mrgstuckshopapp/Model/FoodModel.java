package com.example.mrgstuckshopapp.Model;


import com.google.firebase.firestore.DocumentId;

public class FoodModel {


    @DocumentId
    private String foodid;
    private String description, imageURL, foodname;
    private int price, quantity;

    //these are constructers for information variables
    public FoodModel() {
        this.foodid = foodid;
        this.description = description;
        this.imageURL = imageURL;
        this.foodname = foodname;
        this.price = price;
        this.quantity = quantity;
    }


    //setters and getters for the information variables
    public String getFoodid() {
        return foodid;
    }

    public void setFoodid(String foodid) {
        this.foodid = foodid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    //string variables for the information variables for food
    @Override
    public String toString() {
        return "FoodModel{" +
                "foodid='" + foodid + '\'' +
                ", description='" + description + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", foodname='" + foodname + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}

