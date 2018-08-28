package com.example.mid_year_registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {

    EditText usernameInput, passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        usernameInput = findViewById(R.id.signupUsernameEditText);
        passwordInput = findViewById(R.id.signupPasswordEditText);
    }

    public void onClickButton(View view){
        final String mUsername = usernameInput.getText().toString().trim();
        final String mPassword = passwordInput.getText().toString().trim();

        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        intent.putExtra("signUp email", mUsername);
        intent.putExtra("signUp password", mPassword);
        startActivity(intent);
    }
}
