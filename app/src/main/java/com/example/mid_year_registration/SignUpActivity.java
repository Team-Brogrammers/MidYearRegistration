package com.example.mid_year_registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private static EditText e1;
    private static EditText e2;
    private Button button;
    private CheckBox checkBox;
    private ProgressDialog progressDialog;
    static String userName;
    static String userPass;
    static String checkAdminPrev;
    static String studentNumber;

    /*Firebase Libraies*/
    private FirebaseAuth firebaseAuth;

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
        if(firebaseAuth.getCurrentUser() != null){
            //finish();
            /*Take the user to home*/
        }
        if(getSupportActionBar() != null){
            //enable back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == button){
            registerUser();
        }
    }

    private void registerUser() {
        userName = e1.getText().toString().trim();
        userPass = e2.getText().toString().trim();

        /*Validate user inputs*/
        if(TextUtils.isEmpty(userName)){
            Toast.makeText(getApplicationContext(),
                    "Email cannot be empty",
                    Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(userPass)){
            Toast.makeText(getApplicationContext(),
                    "Password cannot be empty",
                    Toast.LENGTH_SHORT).show();
        }

   /* public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home) {
            Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }*/

        /*Add user information to the database*/
        progressDialog.setMessage("You are being registered...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(userName,userPass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            /*Successfully Registered*/
                            Toast.makeText(getApplicationContext(),
                                    "Registered",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(getApplicationContext(),
                                    "Ooops! We've encountered a problem.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}



