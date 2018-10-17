package com.example.mid_year_registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.mid_year_registration.LoginActivity.isConnectingToInternet;
import static com.example.mid_year_registration.LoginActivity.isValidEmail;
import static com.example.mid_year_registration.LoginActivity.isValidPassword;
//import com.example.lib.ValidateEmail;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private  EditText e1;
    private  EditText e2;
    private Button button;
    private CheckBox checkBox;
    private ConstraintLayout mConstraintLayout;
    private ProgressDialog progressDialog;
    static String userName;
    static String userPass;
    static String checkAdminPrev;
    static String studentNumber;
    static FirebaseUser user = null;


    /*Firebase Libraies*/
    private FirebaseAuth firebaseAuth;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        e1 = findViewById(R.id.usernameEditText);
        e2 = findViewById(R.id.passwordEditText);
        button = (Button) findViewById(R.id.submitButton);
        mConstraintLayout = findViewById(R.id.signupConstraintLayout);
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
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

//        getActionBar().

        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == button){
            registerUser();
        }
    }

    /*Enable the navigation bar back button*/
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /*Take the user back to the login activity*/
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    private void registerUser() {
        userName = e1.getText().toString().trim();
        userPass = e2.getText().toString().trim();

        if(!isValidEmail(userName)){
            e1.setError("Invalid email!");
            return;
        }
        if(!isValidPassword(userPass)){
            e2.setError("Password can't be less than 4 characters or null!");
            return;
        }

        if((userName.endsWith("@wits.ac.za")) || (userName.endsWith("@students.wits.ac.za"))) {
            /*Add user information to the database*/


            /***Check Box should be clicked by a Coordinator Only*/
          if(userName.endsWith("@students.wits.ac.za") && checkBox.isChecked()){
                Snackbar.make(mConstraintLayout, "CheckBox must be checked by a coordinator only!", Snackbar.LENGTH_SHORT).show();
                return;
            }
            else if(!userName.endsWith("@students.wits.ac.za") && !checkBox.isChecked()){
                Snackbar.make(mConstraintLayout, "CheckBox must be checked!", Snackbar.LENGTH_SHORT).show();
                return;
            }

            progressDialog.setMessage("You are being registered...");
            progressDialog.show();
            if( isConnectingToInternet(SignUpActivity.this) == false) {
                Snackbar.make(mConstraintLayout, "No Internet Connection ", Snackbar.LENGTH_LONG).show();
                progressDialog.dismiss();
                return;

            }
            firebaseAuth.createUserWithEmailAndPassword(userName, userPass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                user = FirebaseAuth.getInstance().getCurrentUser();
                                if (user != null)

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
        }else {
            e1.setError("Wits email required");
            return;
        }
    }


    public void SignIn(View view){

        if(user!=null){
            FirebaseAuth.getInstance().getCurrentUser().reload();
            if (user.isEmailVerified()) {
                if (userName.endsWith("@students.wits.ac.za")) {
                    Intent activity = new Intent(SignUpActivity.this, StudentMenuActivity.class);
                    if( isConnectingToInternet(SignUpActivity.this) == false) {
                        Snackbar.make(mConstraintLayout, "No Internet Connection ", Snackbar.LENGTH_LONG).show();
                        //ProgressDialog.dismiss();
                        return;

                    }
                    startActivity(activity);

                } else if (userName.endsWith("@wits.ac.za")) {
                    Intent activity = new Intent(SignUpActivity.this, CoordinatorMenuActivity.class);
                    if( isConnectingToInternet(SignUpActivity.this) == false) {
                        Snackbar.make(mConstraintLayout, "No Internet Connection ", Snackbar.LENGTH_LONG).show();
                        //ProgressDialog.dismiss();
                        return;

                    }
                    startActivity(activity);

                }
            }
        }else{
            Toast.makeText(getApplicationContext(),
                    "Enter your details and Sign Up first!",
                    Toast.LENGTH_SHORT).show();
        }
    }
}



