package com.example.mid_year_registration;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
    }

    public void saveToServer(View v){
        EditText e1 = findViewById(R.id.usernameEditText);
        EditText e2 = findViewById(R.id.passwordEditText);
        String userName = e1.getText().toString();
        String userPass = e2.getText().toString();
        BackgroundTask b1 = new BackgroundTask();
        BackgroundTask b2 = new BackgroundTask();
        b1.execute(userName);
        b2.execute(userPass);
    }

    @SuppressLint("StaticFieldLeak")
    class BackgroundTask extends AsyncTask<String,Void,String>
    {
        String my_url;

        @Override
        protected String doInBackground(String... params) {

            String email_address = params[0];

            try{
                URL url = new URL(my_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String my_data1 = URLEncoder.encode("user_email","UTF-8")+"="+URLEncoder.encode(email_address,"UTF-8");

                bw.write(my_data1);
                bw.flush();
                bw.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                inputStream.close();

                httpURLConnection.disconnect();


            }catch (MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }


            return "Data Inserted";
        }

        @Override
        protected void onPreExecute() {
            my_url = "http://lamp.ms.wits.ac.za/~s1153631/signup.php";
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

}
