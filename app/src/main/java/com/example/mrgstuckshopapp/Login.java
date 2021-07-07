package com.example.mrgstuckshopapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    EditText lEmail, lPassword;
    Button btnLogin;
    TextView lCreate, btnForgot;
    ProgressBar lProgressBar;
    FirebaseAuth fAuth;
    AlertDialog.Builder reset_alert;
    LayoutInflater inflater;
    String userID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        lEmail = findViewById(R.id.l_email);
        lPassword = findViewById(R.id.l_password);
        btnLogin = findViewById(R.id.btn_login);
        lCreate = findViewById(R.id.sign_up);
        lProgressBar = findViewById(R.id.lprogressBar);
        fAuth = FirebaseAuth.getInstance();
        btnForgot = findViewById(R.id.forgotPassword);


        inflater = this.getLayoutInflater();




        reset_alert = new AlertDialog.Builder(this);

        btnForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = inflater.inflate(R.layout.reset_pop, null);

                reset_alert.setTitle("Reset your Password?")
                        .setMessage("Enter your email address here")
                        .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText email = view.findViewById(R.id.rest_email_pop);
                                if(email.getText().toString().isEmpty()){
                                    email.setError("Required Field");
                                    return;
                                }
                                fAuth.sendPasswordResetEmail(email.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(Login.this,"Reset Email Sent", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Login.this,e.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                });



                            }
                        }).setNegativeButton("Cancel", null)
                        .setView(view)
                        .create().show();

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String email = lEmail.getText() .toString() .trim();
                String password = lPassword.getText() .toString() .trim();

                //the conditions


                if(TextUtils.isEmpty(email)){
                    lEmail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)) {
                    lPassword.setError("Password is Required.");
                    return;
                }


                if(password.length() < 8){
                    lPassword.setError("Password must be atleast 8 charaters long");
                    return;
                }

                lProgressBar.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(email, password). addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            final FirebaseUser user = fAuth.getCurrentUser();
                            if (!user.isEmailVerified()) {
                                startActivity(new Intent(getApplicationContext(), verify.class));
                            }
                            else {
                                startActivity(new Intent(getApplicationContext(), HomePage.class));
                            }
                        }

                        else{
                            Toast.makeText(Login.this, "Invalid Credentials" + task.getException() .getMessage(), Toast.LENGTH_SHORT).show();
                            lProgressBar.setVisibility(View.INVISIBLE);
                        }

                    }
                });

            }
        });

        lCreate.setOnClickListener(v -> {
                startActivity(new Intent(getApplicationContext(), Register.class));
        });




        //btnForgot.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //
        //        final EditText resetMail = new EditText(v.getContext());
        //        final AlertDialog.Builder passwordResetDialoge = new AlertDialog.Builder(v.getContext());
        //        passwordResetDialoge.setTitle("Reset Password?");
        //        passwordResetDialoge.setMessage("Enter your email to Reset the Link");
        //        passwordResetDialoge.setView(resetMail);
        //
        //        passwordResetDialoge.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
        //            @Override
        //            public void onClick(DialogInterface dialog, int which) {
        //                //this extracts the email and sends the email link
        //
        //                String mail = resetMail.getText() .toString();
        //                fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
        //                    @Override
        //                    public void onSuccess(Void aVoid) {
        //                        Toast.makeText(Login.this, "Reset Link Sent to your email", Toast.LENGTH_SHORT).show();
        //                    }
        //                }).addOnFailureListener(new OnFailureListener() {
        //                    @Override
        //                    public void onFailure(@NonNull Exception e) {
        //                        Toast.makeText(Login.this, "Error! Reset Link is not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
        //
        //                    }
        //                });
        //            }
        //        });
        //
        //        passwordResetDialoge.setNegativeButton("No", new DialogInterface.OnClickListener() {
        //            @Override
        //            public void onClick(DialogInterface dialog, int which) {
        //                //close the dialouge
        //            }
        //        });
        //
        //    }
        //});
    }
}