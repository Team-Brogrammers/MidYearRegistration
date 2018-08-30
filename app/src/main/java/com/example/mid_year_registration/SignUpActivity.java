package com.example.mid_year_registration;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private static EditText e1;
    private static EditText e2;
    private Button button;
    private CheckBox checkBox;

    static String userName;
    static String userPass;
    static String checkAdminPrev;

    protected void onCreate(Bundle savedInstanceState)  {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        e1 = findViewById(R.id.usernameEditText);
        e2 = findViewById(R.id.passwordEditText);
        button = (Button) findViewById(R.id.submitButton);
        checkBox = (CheckBox) findViewById(R.id.adminCheckBox);

        if(getSupportActionBar() != null){
            //enable back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

//        userName = e1.getText().toString();
//        userPass = e2.getText().toString();
//
//        if(checkBox.isChecked()) {
//            checkAdminPrev = "coordinator";
//        }else{
//            checkAdminPrev = "student";
//        }

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                userName = e1.getText().toString();
                userPass = e2.getText().toString();
                if(checkBox.isChecked()) {
                    checkAdminPrev = "coordinator";
                }else{
                    checkAdminPrev = "student";
                }

                new SummaryAsyncTask().execute((Void) null);

                // Get text from email and password field
                userName = e1.getText().toString();
                if (!isValidEmail(userName)) {
                    //Set error message for email field
                    e1.setError("Invalid Email");
                }

                userPass = e2.getText().toString();
                if (!isValidPassword(userPass)) {
                    //Set error message for password field
                    e2.setError("Password cannot be empty");
                }

                if(isValidEmail(userPass) && isValidPassword(userPass)) {
                    Toast.makeText(SignUpActivity.this, "Account created!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home) {
            Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

     private static class SummaryAsyncTask extends AsyncTask<Void, Void, Boolean> {

        private void postData(String mail, String pass, String check) {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://lamp.ms.wits.ac.za/~s1153631/signup.php");

            try {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(8);
                nameValuePairs.add(new BasicNameValuePair("user_email", mail));
                nameValuePairs.add(new BasicNameValuePair("user_pass", pass));
                nameValuePairs.add(new BasicNameValuePair("user_type", check));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);

            }
            catch(Exception e)
            {
                Log.e("log_tag", "Error:  "+e.toString());
            }
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // Validation Completed
            postData(userName, userPass, checkAdminPrev);

            return null;
        }
    }

    // validating email address
    private static boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // validating password
    private static boolean isValidPassword(String pass) {
        if (pass != null && pass.length() >= 4) {
            return true;
        }
        return false;
    }

}