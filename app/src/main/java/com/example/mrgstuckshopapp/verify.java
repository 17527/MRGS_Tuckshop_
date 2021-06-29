package com.example.mrgstuckshopapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class verify extends AppCompatActivity {
    Button ResendCode;
    String userID;
    FirebaseAuth fAuth;
    TextView Verify;
    private static final String TAG = "verify";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        ResendCode = findViewById(R.id.btn_verify);
        Verify = findViewById(R.id.verifymsg);
        fAuth = FirebaseAuth.getInstance();


        userID = fAuth.getCurrentUser().getUid();
        final FirebaseUser user = fAuth.getCurrentUser();

        if (!user.isEmailVerified()) {
            ResendCode.setVisibility(View.VISIBLE);
            Verify.setVisibility(View.VISIBLE);

            ResendCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (user.isEmailVerified()) {
                        startActivity(new Intent(getApplicationContext(), nav_drawer.class));
                    } else {
                        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(v.getContext(), "Verification link has been sent to the email", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: Email not sent" + e.getMessage());

                            }
                        });
                    }
                }
            });
        }
        else{
            startActivity(new Intent(getApplicationContext(), nav_drawer.class));
            finish();
        }

    }

    public void logout1(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }
}