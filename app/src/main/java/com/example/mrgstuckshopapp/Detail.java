package com.example.mrgstuckshopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mrgstuckshopapp.databinding.ActivityDetailBinding;

public class Detail extends AppCompatActivity {

    ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        final DbHelper helper = new DbHelper(this);

        if (getIntent().getIntExtra("type", 0) == 1) {


            final int image = getIntent().getIntExtra("image", 0);
            final int price = Integer.parseInt(getIntent().getStringExtra("price"));
            String name = getIntent().getStringExtra("name");
            String description = getIntent().getStringExtra("desc");

            binding.detailimage.setImageResource(image);
            binding.pricelbl.setText(String.format("%d", price));
            binding.namelbl.setText(name);
            binding.detailDescription.setText(description);



            binding.insertbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isInserted = helper.insertOrder(
                            binding.namebox.getText().toString(),
                            binding.phonebox.getText().toString(),
                            price,
                            image,
                            name,
                            description,
                            Integer.parseInt(binding.quantity.getText().toString())
                    );

                    if (isInserted)
                        Toast.makeText(Detail.this, "Data Success.", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(Detail.this, "Error.", Toast.LENGTH_SHORT).show();

                }
            });
            // else is for type 2 of accessing detail activity, which is for updating
        } else {
            int id = getIntent().getIntExtra("id", 0);
            Cursor cursor = helper.getOrderById(id);
            int image = cursor.getInt(4);
            binding.detailimage.setImageResource(cursor.getInt(4));
            binding.pricelbl.setText(String.format("%d", cursor.getInt(3)));
            binding.namelbl.setText(cursor.getString(6));
            binding.detailDescription.setText(cursor.getString(7));

            binding.namebox.setText(cursor.getString(1));
            binding.phonebox.setText(cursor.getString(2));
            binding.insertbtn.setText("Update Now");
            binding.insertbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isUpdated = helper.updateOrder(
                            binding.namebox.getText().toString(),
                            binding.phonebox.getText().toString(),
                            Integer.parseInt(binding.pricelbl.getText().toString()),
                            image,
                            binding.detailDescription.getText().toString(),
                            binding.namelbl.getText().toString(),
                            1,
                            id
                            );

                    if(isUpdated)
                        Toast.makeText(Detail.this, "Updated", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(Detail.this, "Error.", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
}