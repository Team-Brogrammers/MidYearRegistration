package com.example.mid_year_registration;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class RegisterUserActivity extends AppCompatActivity {

    EditText e1, e2;
    Button button;
    String userName, userPass;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        e1 = findViewById(R.id.usernameEditText);
        e2 = findViewById(R.id.passwordEditText);
        userName = e1.getText().toString();
        userPass = e2.getText().toString();

        button = (Button) findViewById(R.id.submitButton);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                userName = e1.getText().toString();
                userPass = e2.getText().toString();
                new SummaryAsyncTask().execute((Void) null);
            }
        });
    }

    class SummaryAsyncTask extends AsyncTask<Void, Void, String> {

        private void postData(String mail, String pass) {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://lamp.ms.wits.ac.za/~s1153631/signup.php");

            try {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(8);
                nameValuePairs.add(new BasicNameValuePair("user_email", mail));
                nameValuePairs.add(new BasicNameValuePair("user_pass", pass));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
            }
            catch(Exception e)
            {
                Log.e("log_tag", "Error:  "+e.toString());
            }
        }

        @Override
        protected String doInBackground(Void... params) {
            postData(userName, userPass);
            return "Data Inserted";
        }
    }
}