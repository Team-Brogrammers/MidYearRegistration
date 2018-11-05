package com.example.mid_year_registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
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

import static com.example.mid_year_registration.SignUpActivity.studentNumber;

import static com.example.mid_year_registration.LoginActivity.isConnectingToInternet;

public class StudentConcessionsActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseRef;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    ConstraintLayout mConstraintLayout;

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
        mConstraintLayout = findViewById(R.id.studentconcessions);
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
                if(  isConnectingToInternet(StudentConcessionsActivity.this)== false) {
                    Snackbar.make(mConstraintLayout, "No Internet Connection ", Snackbar.LENGTH_LONG).show();
                    mProgressDialog.dismiss();
                    return;

                }
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


    public void accepted(){


        mNames.clear();
        mImageUrls.clear();
        mStudentNos.clear();
        mCourses.clear();

        DatabaseReference databaseRef1 = database.getReference().child("Concessions");
        mProgressDialog.setTitle("Loading Concessions");
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        final String test = "accepted";
        databaseRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // populate the list with concessions


                for(DataSnapshot childSnap : dataSnapshot.getChildren()) {
                    if (test.equals(childSnap.child("status").getValue())) {
                        //Concessions concession = childSnap.getValue(Concessions.class);
                        CoordinatorConcession concession = childSnap.getValue(CoordinatorConcession.class);
                        String mail = firebaseUser.getEmail();
                        String studentNumber = mail.substring(0, mail.indexOf('@'));
                        if(concession.studentNo.equals(studentNumber)) {

                            Log.d("Concession", concession.getPdfUrl());
                            initImageBitmap(concession.getPdfUrl(), concession.pdfName, concession.studentNo, concession.courseCode, concession.getComment());
                        }


                    }
                    //Toast.makeText(MainActivity.this, "Concession id: "+dataSnapshot.getKey(), Toast.LENGTH_LONG).show();
                    initRecyclerView();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("DB Error", databaseError.toString()); //TODO handle error properly
            }
        });
    }

    public void rejected(){


        mNames.clear();
        mImageUrls.clear();
        mStudentNos.clear();
        mCourses.clear();

        DatabaseReference databaseRef1 = database.getReference().child("Comments");
        final DatabaseReference databaseRef2 = database.getReference().child("Concessions");
        mProgressDialog.setTitle("Loading Concessions");
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        final String test = "rejected";
        databaseRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // populate the list with concessions


                for(DataSnapshot childSnap : dataSnapshot.getChildren()) {
                    //
                    //final String pdfKey = childSnap.getKey();
                    //final String comment = childSnap.getKey();
                    if (test.equals(childSnap.child("status").getValue())) {
                        final String pdfKey = childSnap.child("pdfId").getValue().toString();
                        //Toast.makeText(StudentConcessionsActivity.this, "Concession id: "+pdfKey, Toast.LENGTH_LONG).show();
                        databaseRef2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot childSnap : dataSnapshot.getChildren()) {
                                    //Toast.makeText(MainActivity.this, "Concession id: "+childSnap.getKey(), Toast.LENGTH_LONG).show();
                                    if (pdfKey.equals(childSnap.getKey())) {
                                        CoordinatorConcession concession = childSnap.getValue(CoordinatorConcession.class);
                                        String mail = firebaseUser.getEmail();
                                        String studentNumber = mail.substring(0, mail.indexOf('@'));
                                        if(concession.studentNo.equals(studentNumber)) {
                                            initImageBitmap(concession.getPdfUrl(), concession.pdfName, concession.studentNo, concession.courseCode, concession.getComment());
                                        }
                                    }
                                    initRecyclerView();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                    //Toast.makeText(MainActivity.this, "Concession id: "+dataSnapshot.getKey(), Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("DB Error", databaseError.toString()); //TODO handle error properly
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.student_view_concession_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_logout:
                Intent intent = new Intent(StudentConcessionsActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                return true;

            case R.id.action_accepted:
                //Toast.makeText(ViewConcessionActivity.this, "You selected Accepted", Toast.LENGTH_SHORT).show();
                accepted();
                return  true;
            case R.id.action_rejected:
                //Toast.makeText(StudentConcessionsActivity.this, "You selected Rejected", Toast.LENGTH_SHORT).show();
                rejected();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public ProgressDialog getmProgressDialog() {
        return mProgressDialog;
    }
}
