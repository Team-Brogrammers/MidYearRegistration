package com.example.mid_year_registration;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView editText;
    private TextView textView;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        editText =(TextView)findViewById(R.id.text_message);
        textView =(TextView)findViewById(R.id.view_field);

        Button button = (Button)findViewById(R.id.view_courses);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                mFirestore = FirebaseFirestore.getInstance();

                mFirestore.collection("mid-year-registration-ef4af").document("Courses").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    public static final String FIRE_LOG = "Fire_log";

                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if(task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if(documentSnapshot.exists() && documentSnapshot!= null) {
                                String username = documentSnapshot.getString("math");
                                textView.setText(username);
                            }

                        }
                        else{
                            Log.d(FIRE_LOG, "Erro : "+task.getException().getMessage());
                        }
                    }
                });
            }
        });
    }



    /*public void OnClick(View view){
        Intent intent = new Intent(MainActivity.this,view_module_courses.class);

        startActivity(intent);
        finish();
    }*/

}
