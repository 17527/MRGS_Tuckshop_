package com.example.mrgstuckshopapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mrgstuckshopapp.Models.CartModel;
import com.example.mrgstuckshopapp.R;

import java.util.ArrayList;

public class AdaptorForOrder extends RecyclerView.Adapter<AdaptorForOrder.viewholder>{

    ArrayList<CartModel> list;
        Context context;

    public AdaptorForOrder(ArrayList<CartModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_sample, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        final CartModel model = list.get(position);
        holder.orderImage.setImageResource(model.getOrderImage());
        holder.soldItemName.setText(model.getSoldItemName());
        holder.orderNumer.setText(model.getOrderNumber());
        holder.price.setText(model.getPrice());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        ImageView orderImage;
        TextView soldItemName, orderNumer, price;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            orderImage = itemView.findViewById(R.id.orderimage);
            soldItemName = itemView.findViewById(R.id.orderItemName);
            orderNumer = itemView.findViewById(R.id.ordernumber);
            price = itemView.findViewById(R.id.orderPrice);
        }
    }
}
