package com.example.mid_year_registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lib.ValidateEmail;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private static EditText e1;
    private static EditText e2;
    private Button button;
    private CheckBox checkBox;
    private ProgressDialog progressDialog;
    static String userName;
    static String userPass;
    static String checkAdminPrev;
    static String studentNumber;
    static FirebaseUser user = null;


    /*Firebase Libraies*/
    static FirebaseAuth firebaseAuth;
    LoginActivity loginActivity = new LoginActivity();

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);




        e1 = findViewById(R.id.usernameEditText);
        e2 = findViewById(R.id.passwordEditText);
        button = (Button) findViewById(R.id.submitButton);
        checkBox = (CheckBox) findViewById(R.id.adminCheckBox);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        /*Check whether the user is already signed in*/
        if (firebaseAuth.getCurrentUser() != null) {
            //finish();
            /*Take the user to home*/
        }
        if (getSupportActionBar() != null) {
            //enable back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


    }

    /*Take the user back to the login activity*/
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void registerUser(View view) {
        userName = e1.getText().toString().trim();
        userPass = e2.getText().toString().trim();

        /*Validate user inputs*/
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(getApplicationContext(),
                    "Email cannot be empty",
                    Toast.LENGTH_SHORT).show();
            e1.findFocus();
        }
        if (TextUtils.isEmpty(userPass)) {
            Toast.makeText(getApplicationContext(),
                    "Password cannot be empty",
                    Toast.LENGTH_SHORT).show();
            e2.findFocus();
        }

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(userName).matches() && !TextUtils.isEmpty(userName)){
            Toast.makeText(getApplicationContext(),
                    "Invalid Email format",
                    Toast.LENGTH_SHORT).show();
        }

   if(!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userPass) && android.util.Patterns.EMAIL_ADDRESS.matcher(userName).matches()){
        progressDialog.setMessage("You are being registered...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(userName, userPass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            user = FirebaseAuth.getInstance().getCurrentUser();
                            if(user != null)

                            /*Successfully Registered*/

                            Toast.makeText(getApplicationContext(),
                                    "Registered",
                                    Toast.LENGTH_SHORT).show();

                            if (!user.isEmailVerified()) {
                                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(SignUpActivity.this, "Please click the sent Verification Link to your email", Toast.LENGTH_LONG).show();

                                    }
                                });

                            }



                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Ooops! Email Account already in use!.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }}


    public void SignIn(View view){

        if(user!=null){
        FirebaseAuth.getInstance().getCurrentUser().reload();
        if (user.isEmailVerified()) {
            Toast.makeText(getApplicationContext(), "Email Verified!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));




        }}

        else{
            Toast.makeText(getApplicationContext(),
                    "Enter your details and Sign Up first!",
                    Toast.LENGTH_SHORT).show();

        }
    }


}






