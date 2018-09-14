package com.example.mid_year_registration;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class view_module_courses extends AppCompatActivity {

    private TextView editText;
    private TextView textView;
    private FirebaseFirestore mFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_module_courses);

        editText =(TextView)findViewById(R.id.text_message);
        textView =(TextView)findViewById(R.id.view_field);

        CollectCourses();
    }

    public void CollectCourses(){
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

}
