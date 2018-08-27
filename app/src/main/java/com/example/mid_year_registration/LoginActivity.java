package com.example.mid_year_registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        NeedAccount();
    }



    public void onClickButton(View view){

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

   public void NeedAccount(){
        TextView textView = (TextView)findViewById(R.id.creatAccountTextView);
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
