package com.example.mid_year_registration;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mStudentNos = new ArrayList<>();
    private ArrayList<String> mCourses = new ArrayList<>();

    private ProgressDialog mProgressDialog;

    private static final String TAG="MainActivity";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        getSupportActionBar().setTitle("Student Requests");
        /* Set up the action bar */
        if(getSupportActionBar() != null){
            //enable back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


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
                for(DataSnapshot childSnap : dataSnapshot.getChildren()){
                    Concessions concession = childSnap.getValue(Concessions.class);
                    Log.d("Concession", concession.getPdfUrl());

                    initImageBitmap(concession.getPdfUrl(), concession.pdfName, concession.studentNo, concession.courseCode);
                }
                //Toast.makeText(MainActivity.this, "Concession id: "+dataSnapshot.getKey(), Toast.LENGTH_LONG).show();
                initRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("DB Error", databaseError.toString()); //TODO handle error properly
            }
        });

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
                        Concessions concession = childSnap.getValue(Concessions.class);
                        Log.d("Concession", concession.getPdfUrl());

                        initImageBitmap(concession.getPdfUrl(), concession.pdfName, concession.studentNo, concession.courseCode);
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

    public void pending(){


        mNames.clear();
        mImageUrls.clear();
        mStudentNos.clear();
        mCourses.clear();

        DatabaseReference databaseRef1 = database.getReference().child("Concessions");
        mProgressDialog.setTitle("Loading Concessions");
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        final String test = "pending";  // string for student numbers
        databaseRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // populate the list with concessions


                for(DataSnapshot childSnap : dataSnapshot.getChildren()) {
                    if (test.equals(childSnap.child("status").getValue())) { // course code or student number
                        Concessions concession = childSnap.getValue(Concessions.class);
                        Log.d("Concession", concession.getPdfUrl());

                        initImageBitmap(concession.getPdfUrl(), concession.pdfName, concession.studentNo, concession.courseCode);
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
                        Toast.makeText(MainActivity.this, "Concession id: "+pdfKey, Toast.LENGTH_LONG).show();
                        databaseRef2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot childSnap : dataSnapshot.getChildren()) {
                                    //Toast.makeText(MainActivity.this, "Concession id: "+childSnap.getKey(), Toast.LENGTH_LONG).show();
                                    if (pdfKey.equals(childSnap.getKey())) {
                                        Concessions concession = childSnap.getValue(Concessions.class);
                                        Log.d("Concession", concession.getPdfUrl());

                                        initImageBitmap(concession.getPdfUrl(), concession.pdfName, concession.studentNo, concession.courseCode);
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
        getMenuInflater().inflate(R.menu.menu_search, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_btn).getActionView();

        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.requestFocus(1);
        Toast.makeText(MainActivity.this, "Search Complete", Toast.LENGTH_SHORT).show();

        return super.onCreateOptionsMenu(menu);
        //return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.action_logout:
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                return true;

            case R.id.action_accepted:
                //Toast.makeText(ViewConcessionActivity.this, "You selected Accepted", Toast.LENGTH_SHORT).show();
                accepted();
                return  true;
            case R.id.action_rejected:
                //Toast.makeText(MainActivity.this, "You selected Rejected", Toast.LENGTH_SHORT).show();
                rejected();
                return true;

            case R.id.action_pending:
                //Toast.makeText(MainActivity.this, "You selected Pending", Toast.LENGTH_SHORT).show();
                pending();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }
    public ProgressDialog getmProgressDialog() {
        return mProgressDialog;
    }
}
