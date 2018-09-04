package com.example.mid_year_registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    // Firebase instance variables
    private FirebaseAuth mAuth;

    //Reference variables
    private ProgressDialog mProgressDialog;
    //private EditText etEmail, etPassword;
    private ConstraintLayout mConstraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        mProgressDialog = new ProgressDialog(this);

        // Get Reference to variables
//        etEmail = (EditText) findViewById(R.id.emailEditText);
//        etPassword = (EditText) findViewById(R.id.passwordEditText);
        mConstraintLayout = findViewById(R.id.loginConstraintLayout);

    }

    /* Triggers when LOGIN Button clicked
     * This is the sign in method, it is going to check if users are signed in.
     * if they are there then we don't do anything we'll just adjust the views.
     * if they aren't signed in then we try to sign them in, provided they gave
     * correct credentials.
     * */
    public void checkLogin(View arg0) {
        String email = ((EditText) findViewById(R.id.emailEditText)).getText().toString();
        String password = ((EditText) findViewById(R.id.passwordEditText)).getText().toString();

        if(!isValidEmail(email)){return;}
        if(!isValidPassword(password)){return;}

        mProgressDialog.setTitle("Logging In");
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mProgressDialog.dismiss();
                            Intent activity = new Intent(LoginActivity.this, StudentUpload.class);
                            startActivity(activity);
                            finish();

                        } else {
                            mProgressDialog.dismiss();
                            Snackbar.make(mConstraintLayout, "Authentication Failed, Invalid Email or Password!", Snackbar.LENGTH_LONG ).show();

                        }
                    }
                });



    }

    // validating email address
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // validating password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() >= 4) {
            return true;
        }
        return false;
    }

    // Triggers when Need an account? text is clicked
    public void needAccount(View view) {
        Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
        startActivity(intent);
        LoginActivity.this.finish();
    }

}
