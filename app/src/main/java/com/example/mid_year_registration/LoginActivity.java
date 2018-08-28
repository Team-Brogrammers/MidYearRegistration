package com.example.mid_year_registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    EditText emailInput, passwordInput;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        emailInput = findViewById(R.id.loginEmailEditText);
        passwordInput = findViewById(R.id.loginPasswordEditText);

        NeedAccount();
    }



    public void onClickButton(View view){
        final String mEmail = emailInput.getText().toString().trim();
        final String mPassword = passwordInput.getText().toString().trim();

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("signUp email", mEmail);
        intent.putExtra("signUp password", mPassword);
        startActivity(intent);
    }

   public void NeedAccount(){
        TextView textView = findViewById(R.id.creatAccountTextView);
        textView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                        startActivity(intent);
                    }
                }
        );

}

}
