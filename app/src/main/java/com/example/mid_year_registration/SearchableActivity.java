package com.example.mid_year_registration;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchableActivity extends AppCompatActivity {

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mStudentNos = new ArrayList<>();
    private ArrayList<String> mCourses = new ArrayList<>();
    private ProgressDialog mProgressDialog;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        Intent intent = getIntent();
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
    }

    private void initImageBitmap(String url, String name, String studentNo, String course){
       // Log.d(TAG, "initImageBitmaps: preparing bitmaps");

        mImageUrls.add(url);
        mNames.add(name);
        mStudentNos.add(studentNo);
        mCourses.add(course);
    }

    private void initRecyclerView(){
       // Log.d(TAG, "initRecyclerView: init RecyclerView");
        RecyclerView recyclerView=findViewById(R.id.searchRecyclerview);
        RecyclerViewAdapter3 adapter= new RecyclerViewAdapter3(this, mNames, mImageUrls, mStudentNos, mCourses);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
       // mProgressDialog.dismiss();
    }

    private void doMySearch(final String search_string) {
        Toast.makeText(SearchableActivity.this, "Search Complete" +search_string, Toast.LENGTH_SHORT).show();

        DatabaseReference databaseRef1 = database.getReference().child("Concessions");
       // mProgressDialog.setTitle("Loading Concessions");
      //  mProgressDialog.setMessage("Please wait...");
       // mProgressDialog.setCanceledOnTouchOutside(false);
        //mProgressDialog.show();

        Query query =  databaseRef1.orderByChild("studentNo")
                .startAt(search_string)
                .endAt(search_string+"\uf8ff");
        //final String test = "accepted";
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // populate the list with concessions


                for(DataSnapshot childSnap : dataSnapshot.getChildren()) {
                    if (search_string.equals(childSnap.child("studentNo").getValue())) {
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




}
