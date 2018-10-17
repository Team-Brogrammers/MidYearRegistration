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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.mid_year_registration.LoginActivity.isConnectingToInternet;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mStudentNos = new ArrayList<>();
    private ArrayList<String> mCourses = new ArrayList<>();
    private ProgressDialog mProgressDialog;

    private static final String TAG="MainActivity";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseRef;
    ConstraintLayout mConstraintLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mConstraintLayout = findViewById(R.id.activitymain);
        getSupportActionBar().setTitle("Student Requests");
        /* Set up the action bar */


        if(getSupportActionBar() != null){
            //enable back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if( isConnectingToInternet(MainActivity.this) == false) {
            Snackbar.make(mConstraintLayout, "No Internet Connection ", Snackbar.LENGTH_LONG).show();

            //mProgressDialog.dismiss();
            return;

        }

        else {

            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("Loading Concessions");
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();


            databaseRef = database.getReference().child("Concessions");

            databaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // populate the list with concessions


                    for (DataSnapshot childSnap : dataSnapshot.getChildren()) {
                        Concessions concession = childSnap.getValue(Concessions.class);
                        initImageBitmap(concession.getPdfUrl(), concession.pdfName, concession.studentNo, concession.courseCode);
                    }
                    initRecyclerView();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("DB Error", databaseError.toString()); //TODO handle error properly
                }
            });
        }
    }

    private void initImageBitmap(String url, String name, String studentNo, String course){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps");

        mImageUrls.add(url);
        mNames.add(name);
        mStudentNos.add(studentNo);
        mCourses.add(course);
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init RecyclerView");
        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        RecyclerViewAdapter adapter= new RecyclerViewAdapter(this, mNames, mImageUrls, mStudentNos, mCourses);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressDialog.dismiss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_logout) {
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
        if(item.getItemId() == android.R.id.home){
            Intent intent = new Intent(MainActivity.this,CoordinatorMenuActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public ProgressDialog getmProgressDialog() {
        return mProgressDialog;
    }
}
