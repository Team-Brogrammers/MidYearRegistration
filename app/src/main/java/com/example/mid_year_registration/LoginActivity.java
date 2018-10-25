package com.example.mid_year_registration;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class LoginActivity extends AppCompatActivity {

    // Firebase instance variables
    private FirebaseAuth mAuth;

    //Reference variables
    private ProgressDialog mProgressDialog;
   static private EditText etEmail;
    private EditText etPassword;
    private ConstraintLayout mConstraintLayout;
    static FirebaseUser user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        mProgressDialog = new ProgressDialog(this);
        getSupportActionBar().setTitle("Mid Year Registration");

        // Get Reference to variables
        etEmail = (EditText) findViewById(R.id.emailEditText);
        etPassword = (EditText) findViewById(R.id.passwordEditText);
        mConstraintLayout = findViewById(R.id.loginConstraintLayout);


    }

    /* Triggers when LOGIN Button clicked
     * This is the sign in method, it is going to check if users are signed in.
     * if they are there then we don't do anything we'll just adjust the views.
     * if they aren't signed in then we try to sign them in, provided they gave
     * correct credentials.
     * */


    public void checkLogin(final View arg0) {
        final String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if(!isValidEmail(email)){
            etEmail.setError("Invalid email!");
            return;
        }
        if(!isValidPassword(password)){
            etPassword.setError("Password can't be less than 4 characters or null!");
            return;
        }



        if((email.endsWith("@wits.ac.za")) || (email.endsWith("@students.wits.ac.za"))) {
            mProgressDialog.setTitle("Logging In");
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();


            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull final Task<AuthResult> task) {
                            if (isConnectingToInternet(LoginActivity.this) == false) {
                                Snackbar.make(mConstraintLayout, "No Internet Connection ", Snackbar.LENGTH_LONG).show();
                                mProgressDialog.dismiss();
                                return;

                            }
                            else if( isConnectingToInternet(LoginActivity.this) == true){
                            mAuth = FirebaseAuth.getInstance();
                            mAuth.fetchProvidersForEmail(etEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener <ProviderQueryResult>() {

                                @Override
                                public void onComplete(@NonNull Task <ProviderQueryResult> task1) {
                                    if (!task1.getResult().getProviders().isEmpty()) {

                                        if (user != null) {
                                            FirebaseAuth.getInstance().getCurrentUser().reload();
                                            if (!user.isEmailVerified()) {
                                                mProgressDialog.dismiss();
                                                Snackbar.make(mConstraintLayout, "Unverified Email", Snackbar.LENGTH_LONG)
                                                        .setAction("RESEND ME VERIFICATION EMAIL", new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View view) {
                                                                user.sendEmailVerification();
                                                            }
                                                        })
                                                        .setActionTextColor(Color.GREEN)
                                                        .show();

                                                // Toast.makeText(LoginActivity.this, "Please click the sent Verification Link to your email", Toast.LENGTH_LONG).show();

                                                return;
                                            }
                                        }

                                        if (task.isSuccessful()) {


                                            mProgressDialog.dismiss();

                                            String personNumber = email.substring(0, email.indexOf("@"));
                                            if (email.endsWith("@students.wits.ac.za")) {
                                                Intent activity = new Intent(LoginActivity.this, StudentMenuActivity.class);
                                                activity.putExtra("personNumber", personNumber);
                                                startActivity(activity);

                                            } else if (email.endsWith("@wits.ac.za")) {
                                                Intent activity = new Intent(LoginActivity.this, CoordinatorMenuActivity.class);
                                                activity.putExtra("personNumber", personNumber);
                                                startActivity(activity);

                                            }

                                        } else {
                                            //mProgressDialog.dismiss();
                                            Snackbar.make(mConstraintLayout, "Authentication Failed, wrong Password!", Snackbar.LENGTH_LONG).show();
                                            mProgressDialog.dismiss();

                                        }

                                    } else {
                                        Snackbar.make(mConstraintLayout, "You are not registered, please sign up  first!", Snackbar.LENGTH_LONG).show();
                                        mProgressDialog.dismiss();
                                        return;
                                    }
                                }
                            });
                        }
                        }
                    });


        }else {
            etEmail.setError("Wits email required");
            return;
        }


    }



    /************Checking if Mobile data is Avalaible*****/
   public static boolean isOnline() {

        try {

            Process p1 = Runtime.getRuntime().exec("ping -c 1 www.google.com");
            int returnVal = p1.waitFor();
            boolean reachable = (returnVal==0);

            if(reachable){
                //System.out.println("Internet access");
                return true;
            }
            else{
                //System.out.println("No Internet access");
                return  false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /***Checking wifi Connection***/
    static boolean wifiIsConnected = true;
    final static FirebaseDatabase database = FirebaseDatabase.getInstance();

    public static boolean hostAvailable() {

        final DatabaseReference connectedRef = database.getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    wifiIsConnected = true;
                }
                else{
                    wifiIsConnected = false;
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    final DatabaseReference myConnectionsRef = database.getReference("users/connections");
                    DatabaseReference con = myConnectionsRef.push();
                    con.onDisconnect().removeValue();
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
        return wifiIsConnected;
    }
    public static boolean isConnectingToInternet(Context context) {

        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//mobile
        NetworkInfo.State mobile = conMan.getNetworkInfo(0).getState();
//wifi
        NetworkInfo.State wifi = conMan.getNetworkInfo(1).getState();
        if (mobile == NetworkInfo.State.CONNECTED || mobile == NetworkInfo.State.CONNECTING )
        {
            if( isOnline()== true) {
                return true;
            }
        }
        else if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING  ) {

            if (hostAvailable() == true) {
                return true;
            }
        }
        return false;
    }

    // validating email address
    static boolean isValidEmail(String email) {
        if (email != null){
            String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            Pattern pattern = Pattern.compile(EMAIL_PATTERN);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }
        return false;
    }

    // validating password
    static boolean isValidPassword(String pass) {
        if (pass != null && pass.length() >= 4) {
            return true;
        }

        return false;
    }


    // Triggers when Need an account? text is clicked
    public void needAccount(View view) {
        Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    public void resetPassword(View view) {
        Intent intent = new Intent(LoginActivity.this,PasswordResetActivity.class);
        startActivity(intent);
        finish();
    }
}
