package com.example.mid_year_registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.ProviderQueryResult;

import java.util.List;

import static com.example.mid_year_registration.LoginActivity.isValidEmail;

public class PasswordResetActivity extends AppCompatActivity {

    // References variables
    private EditText emailEditText;
    private Button passwordResetButton;
    private ConstraintLayout constraintLayout;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_reset_activity);

        emailEditText = findViewById(R.id.resetPasswordEditText);
        passwordResetButton = findViewById(R.id.resetPasswordButton);
        constraintLayout = findViewById(R.id.resetPasswordConstraintLayout);

        progressDialog = new ProgressDialog(this);
        getSupportActionBar().setTitle("Reset Password");

        // Initialize Firebase components
        mAuth = FirebaseAuth.getInstance();

        passwordResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailEditText.getText().toString();
                checkEmailExistsInFirebase(email);
            }
        });

    }


    protected boolean isvalideEmail() {
        String email = emailEditText.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            emailEditText.setError("Invalid email address");
            return false;
        }

        return true;
    }

    protected void checkEmailExistsInFirebase(final String email) {
        if(!isvalideEmail()) return;

        progressDialog.setTitle("Resetting Password");
        progressDialog.setMessage("Please wait while we send you an email with reset password link");
        progressDialog.setCanceledOnTouchOutside(false);
        //progressDialog.show();

        mAuth = FirebaseAuth.getInstance();

        mAuth.fetchProvidersForEmail(email).addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {

            @Override
            public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                if(task.getResult().getProviders() != null){
                    resetPassword(task.getResult().getProviders(), email);

                }
            }
        });

    }

    // this function send the user an account reset email so that they can change their password.
    private void resetPassword(List<String> providers, final String email) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        Boolean flag = false;
        for (String provider : providers) {
            if (provider.equals("password")) {
                flag = true;
                break;
            }
        }
        if(!flag){
           // progressDialog.dismiss();
            Snackbar.make(constraintLayout, "An account with this Email Doesn't Exists!", Snackbar.LENGTH_LONG ).show();
            return;
        }
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                           // progressDialog.dismiss();
                            Snackbar.make(constraintLayout, "Click on the link sent to your email to reset your password", Snackbar.LENGTH_INDEFINITE)
                                    .setAction("LOGIN", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent activity = new Intent(PasswordResetActivity.this,LoginActivity.class);
                                            startActivity(activity);
                                            finish();
                                        }})
                                    .setActionTextColor(Color.GREEN)
                                    .show();
                        }else{
                           // progressDialog.dismiss();
                            Snackbar.make(constraintLayout, "Error! Failed to send email, wait while we send it again", Snackbar.LENGTH_LONG)
                                    .setAction("RETRY", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            checkEmailExistsInFirebase(email);
                                        }})
                                    .setActionTextColor(Color.RED)
                                    .show();

                        }
                    }
                });
    }

}
