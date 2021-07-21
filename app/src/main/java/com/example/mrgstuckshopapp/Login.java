package com.example.mrgstuckshopapp;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {


    //setting the variables

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

        //full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);


        //setting the ids for variables
        lEmail = findViewById(R.id.l_email);
        lPassword = findViewById(R.id.l_password);
        btnLogin = findViewById(R.id.btn_login);
        lCreate = findViewById(R.id.sign_up);
        lProgressBar = findViewById(R.id.lprogressBar);
        fAuth = FirebaseAuth.getInstance();
        btnForgot = findViewById(R.id.forgotPassword);


        //inflates the layout
        inflater = this.getLayoutInflater();


        //opens a pop-up when reset button is clicked
        reset_alert = new AlertDialog.Builder(this);

        btnForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = inflater.inflate(R.layout.reset_pop, null);

                //how the pop-up will look like
                reset_alert.setTitle("Reset your Password?")
                        .setMessage("Enter your email address here")

                        .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText email = view.findViewById(R.id.rest_email_pop);
                                //if they click on reset button and the email field is empty then asks them to enter it again
                                if(email.getText().toString().isEmpty()){
                                    email.setError("Required Field");
                                    return;
                                } //if its not empty then it sends reset email to the entered email address
                                fAuth.sendPasswordResetEmail(email.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(Login.this,"Reset Email Sent", Toast.LENGTH_SHORT).show();
                                    }

                                    //if it fails then it shows the message why it failed
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Login.this,e.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                });



                            }

                            //cancel button closes the alert dialouge
                        }).setNegativeButton("Cancel", null)
                        .setView(view)
                        .create().show();

            }
        });


        //login button
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

                //the spinning bar
                lProgressBar.setVisibility(View.VISIBLE);

                //checks the email and password in firebase
                fAuth.signInWithEmailAndPassword(email, password). addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {

                        //if the email and password match, it checks if the user has verified or not
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            final FirebaseUser user = fAuth.getCurrentUser();
                            //if not verified then it opens verify activity
                            if (!user.isEmailVerified()) {
                                startActivity(new Intent(getApplicationContext(), Verify.class));
                            }
                            //if verified then they are granted in the home page of the app
                            else {
                                startActivity(new Intent(getApplicationContext(), HomePage.class));
                            }
                        }


                        //if the task is not succesful, it says invalid credentials and also displays the error then makes the spiining wheel disappear
                        else{
                            Toast.makeText(Login.this, "Invalid Credentials " + task.getException() .getMessage(), Toast.LENGTH_SHORT).show();
                            lProgressBar.setVisibility(View.INVISIBLE);
                        }

                    }
                });

            }
        });

        //if the user clicks on 'Create Account' button then it opens register activity
        lCreate.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), Register.class));
        });





    }
}