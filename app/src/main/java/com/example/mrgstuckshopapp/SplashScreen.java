package com.example.mrgstuckshopapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_SCREEN = 5000;

    //Variables
    Animation topAnim, bottomAnim;
    ImageView image;
    TextView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);

        //animations
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //Hooks
        image = findViewById(R.id.imageView);
        logo = findViewById(R.id.tuckshop_intro);

        //setting the animation to variables
        image.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);


        //sending them to register class, once the splash screen is completed
        new Handler().postDelayed (new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, Register.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
    }
}