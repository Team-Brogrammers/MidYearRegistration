package com.example.mid_year_registration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        //Example retrieving from signUpActivity
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b!=null){
            String email=(String) b.get("signUp email");
            String password=(String) b.get("signUp password");

        }

    }

}
