package com.example.mid_year_registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentConcessionsActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseRef;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;

    private ProgressDialog mProgressDialog;

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mStudentNos = new ArrayList<>();
    private ArrayList<String> mCourses = new ArrayList<>();
    private ArrayList<String> mComments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_concessions);
        getSupportActionBar().setTitle("My Requests");

        /* Set up the action bar */
        if(getSupportActionBar() != null){
            //enable back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mProgressDialog = new ProgressDialog(StudentConcessionsActivity.this);
        mProgressDialog.setTitle("Loading Concessions");
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        databaseRef = database.getReference().child("Concessions");
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // populate the list with concessions
                for(DataSnapshot childSnap : dataSnapshot.getChildren()){
                    CoordinatorConcession concession = childSnap.getValue(CoordinatorConcession.class);
                    if(firebaseUser != null){
                        String mail = firebaseUser.getEmail();
                        String studentNumber = mail.substring(0, mail.indexOf('@'));
                        if(concession.studentNo.equals(studentNumber)){
                            initImageBitmap(concession.getPdfUrl(), concession.pdfName, concession.studentNo, concession.courseCode, concession.comment);
                        }
                    }
                    else {
                        Toast.makeText(StudentConcessionsActivity.this, "You are not logged in!", Toast.LENGTH_SHORT);
                    }
                }
                initRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("DB Error", databaseError.toString()); //TODO handle error properly
            }
        });
    }

    private void initImageBitmap(String url, String name, String studentNo, String course, String comment){

        mImageUrls.add(url);
        mNames.add(name);
        mStudentNos.add(studentNo);
        mCourses.add(course);
        mComments.add(comment);
    }

    private void initRecyclerView(){

        RecyclerView recyclerView = findViewById(R.id.studentRecyclerView);
        RecyclerViewAdapter2 adapter = new RecyclerViewAdapter2(this, mNames, mImageUrls, mStudentNos, mCourses, mComments);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressDialog.dismiss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.student_view_concession_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_logout) {
            Intent intent = new Intent(StudentConcessionsActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        if(item.getItemId() == android.R.id.home){
            Intent intent = new Intent(StudentConcessionsActivity.this, StudentMenuActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public ProgressDialog getmProgressDialog() {
        return mProgressDialog;
    }
}
