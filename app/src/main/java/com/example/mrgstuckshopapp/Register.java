package com.example.mrgstuckshopapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {
    EditText rEmail, rStudID, rPassword;
    Button btnRegister;
    ImageView rLogo;
    FirebaseAuth fAuth;
    ProgressBar rProgressBar;
    TextView rCreate;

    private static final String TAG = "Register";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);


        rEmail = findViewById(R.id.r_email);
        rStudID = findViewById(R.id.stud_id);
        rPassword = findViewById(R.id.r_password);
        btnRegister = findViewById(R.id.btn_register);
        rLogo = findViewById(R.id.big_logo);
        rCreate = findViewById(R.id.log_in);
        fAuth = FirebaseAuth.getInstance();
        rProgressBar = findViewById(R.id.r_progressbar);





        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), HomePage.class));
            finish();
        }

        btnRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = rEmail.getText() .toString() .trim();
                String password = rPassword.getText() .toString() .trim();
                String stud_id = rStudID.getText() .toString() .trim();



                //the conditions

                if(TextUtils.isEmpty(stud_id)){
                    rStudID.setError("Student ID is required");
                    return;
                }

                if(TextUtils.isEmpty(email)){
                    rEmail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)) {
                    rPassword.setError("Password is Required.");
                    return;
                }

                if(stud_id.length() != 5){
                    rStudID.setError("Please enter a Valid Student ID");
                    return;
                }

                if(password.length() < 8){
                    rPassword.setError("Please choose a password more than 8 characters long");
                    return;
                }

                rProgressBar.setVisibility(View.VISIBLE);

                //registering the user into firebase

                fAuth.createUserWithEmailAndPassword(email,password) .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            FirebaseUser user = fAuth.getCurrentUser();
                            user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Register.this,"Verification link has been sent to the email", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: Email not sent" + e.getMessage());

                                }
                            });
                            Toast.makeText(Register.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Verify.class));
                            finish();
                        }

                        else{
                            Toast.makeText(Register.this, "Error" + task.getException() .getMessage(), Toast.LENGTH_SHORT).show();
                            rProgressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });


            }
        });

        rCreate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });




        }
    }
