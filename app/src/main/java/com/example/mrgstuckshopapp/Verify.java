package com.example.mrgstuckshopapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Verify extends AppCompatActivity {
    //setting the variables
    Button ResendCode;
    String userID;
    FirebaseAuth fAuth;
    TextView Verify;
    private static final String TAG = "Verify";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        //hooking ids with variables
        ResendCode = findViewById(R.id.btn_verify);
        Verify = findViewById(R.id.verifymsg);
        fAuth = FirebaseAuth.getInstance();


        //gets the current user
        userID = fAuth.getCurrentUser().getUid();
        final FirebaseUser user = fAuth.getCurrentUser();

        //if the user is not verified then makes the text and button visible
        if (!user.isEmailVerified()) {
            ResendCode.setVisibility(View.VISIBLE);
            Verify.setVisibility(View.VISIBLE);

            //resend button sends verification email again if it not sent
            ResendCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(v.getContext(), "Verification link has been sent to the email", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: Email not sent " + e.getMessage());

                        }
                    });

                }
            });
            //if the email is verified, then it automatically sends the user to homepage
        } else {
            startActivity(new Intent(getApplicationContext(), HomePage.class));
            finish();
        }

    }

    //logout function
    public void logout1(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }
}