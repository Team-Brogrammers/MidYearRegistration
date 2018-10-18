package com.example.mid_year_registration;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.google.firebase.auth.FirebaseUser;
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
    private EditText etEmail;
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



        static ArrayList<String> arrayList = new ArrayList<>();
        static boolean RegisteredEmail(String email){
            arrayList.add(email);
            return true;
        }

    public void checkLogin(View arg0) {
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

        if((email.contains("@wits.ac.za")) || (email.contains("@students.wits.ac.za"))) {
            mProgressDialog.setTitle("Logging In");
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();


            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if( isConnectingToInternet(LoginActivity.this) == false) {
                                Snackbar.make(mConstraintLayout, "No Internet Connection ", Snackbar.LENGTH_LONG).show();
                                mProgressDialog.dismiss();
                                return;

                            }

                            if (task.isSuccessful()) {


                               mProgressDialog.dismiss();
                                if (email.endsWith("@students.wits.ac.za")) {
                                    Intent activity = new Intent(LoginActivity.this, StudentMenuActivity.class);
                                    startActivity(activity);

                                } else if (email.endsWith("@wits.ac.za")) {
                                    Intent activity = new Intent(LoginActivity.this, CoordinatorMenuActivity.class);
                                    startActivity(activity);

                                }

                            } else {
                                //mProgressDialog.dismiss();
                                Snackbar.make(mConstraintLayout, "Authentication Failed, Invalid Email or Password!", Snackbar.LENGTH_LONG).show();
                                mProgressDialog.dismiss();

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

    /***Checking Internert Connection***/
    static boolean wifiIsConnected = true;


    static final FirebaseDatabase database = FirebaseDatabase.getInstance();

    public static boolean hostAvailable() {

      /*   DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");



        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    //System.out.println("connected");
                    wifiIsConnected = true;
                } else {
                    //System.out.println("not connected");
                    wifiIsConnected = false;
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                //System.err.println("Listener was cancelled");
            }
        });*/

        // since I can connect from multiple devices, we store each connection instance separately
// any time that connectionsRef's value is null (i.e. has no children) I am offline


// stores the timestamp of my last disconnect (the last time I was seen online)
       // final DatabaseReference lastOnlineRef = database.getReference("/users/"+user+"/lastOnline");

        final DatabaseReference connectedRef = database.getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {

                    wifiIsConnected = true;
                    // when this device disconnects, remove it


                }

                else{
                    wifiIsConnected = false;
                    //user = FirebaseAuth.getInstance().getCurrentUser();
                    final DatabaseReference myConnectionsRef = database.getReference(".info/connected");
                    DatabaseReference con = myConnectionsRef.push();
                    con.onDisconnect().removeValue();
                }


                // when I disconnect, update the last time I was seen online
                //lastOnlineRef.onDisconnect().setValue(ServerValue.TIMESTAMP);

                // add this device to my connections list
                // this value could contain info about the device or a timestamp too
               // con.setValue(Boolean.TRUE);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                //System.err.println("Listener was cancelled at .info/connected");
            }
        });
        return wifiIsConnected;
    }
    public static boolean isConnectingToInternet(Context context) {
        //FirebaseAuth.getInstance().getCurrentUser().reload();

        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

//mobile
        NetworkInfo.State mobile = conMan.getNetworkInfo(0).getState();

//wifi
        NetworkInfo.State wifi = conMan.getNetworkInfo(1).getState();


        if (mobile == NetworkInfo.State.CONNECTED || mobile == NetworkInfo.State.CONNECTING )
        {

            if(hostAvailable()== true) {
                return true;
            }

        }

        else if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING  ){


            if(hostAvailable()== true) {
                return true;
            }

        }


        return false;
    }

    /********************/

  /* public static boolean hasInternetAccess(Context context) {
       //String TAG = "Activity";
       if (isConnectingToInternet(context) == true) {
           try {
               HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
               urlc.setRequestProperty("User-Agent", "Test");
               urlc.setRequestProperty("Connection", "close");
               urlc.setConnectTimeout(1500);
               urlc.connect();

               if (urlc.getResponseCode() == 200) {
                   return true;
               }
           } catch (IOException e) {

               // Log.e(LOG_TAG, "Error checking internet connection", e);
           }
       }

       return false;
   }*/


    //static  boolean  dum_variable = true;
  /*  public  void TimeOut()  {



        boolean isInternetAvailable = isConnectingToInternet(this);

        if(isInternetAvailable == true) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Toast.makeText(LoginActivity.this, "No INTERNET CHECK YOUR CONNECTION", Toast.LENGTH_SHORT).show();
                    try {
                        InetAddress ipAddr = InetAddress.getByName("www.google.com"); //You can replace it with your name
                        if(!ipAddr.equals("")){
                            dum_variable = true;
                        }
                    } catch (Exception e) {
                        dum_variable = false;
                    }

                }
            }, 10);
        }

        else if(isInternetAvailable == false){
            dum_variable = false;*/
       /* }

    /*}******/

   /* public static boolean hasInternetAccess(){


        return dum_variable;
    }*/



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
