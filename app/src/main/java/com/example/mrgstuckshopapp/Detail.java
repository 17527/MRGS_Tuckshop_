package com.example.mrgstuckshopapp;

import androidx.appcompat.app.AppCompatActivity;

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

        final int image = getIntent().getIntExtra("image", 0);
        final int price = Integer.parseInt(getIntent().getStringExtra("price"));
        String name = getIntent().getStringExtra("name");
        String description = getIntent().getStringExtra("desc");

        binding.detailimage.setImageResource(image);
        binding.pricelbl.setText(String.format("%d",price));
        binding.namelbl.setText(name);
        binding.detailDescription.setText(description);

        DbHelper helper = new DbHelper(this);

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

                if(isInserted)
                    Toast.makeText(Detail.this, "Data Success.", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(Detail.this, "Error.", Toast.LENGTH_SHORT).show();

            }
        });
    }
}