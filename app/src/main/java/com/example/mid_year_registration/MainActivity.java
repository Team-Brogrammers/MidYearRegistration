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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> mNames=new ArrayList<>();
    private ArrayList<String> mImageUrls=new ArrayList<>();

    private static final String TAG="MainActivity";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Log.d(TAG, "onCreate: started");

        databaseRef = database.getReference().child("Concessions");

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // populate the list with concessions
                for(DataSnapshot childSnap : dataSnapshot.getChildren()){
                    Concessions concession = childSnap.getValue(Concessions.class);
                    Log.d("Concession", concession.getPdfUrl());
                    initImageBitmap(concession.getPdfUrl(), concession.courseCode);
                }
                initRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("DB Error", databaseError.toString()); //TODO handle error properly
            }
        });

    }

    private void initImageBitmap(String url, String course){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps");

        mImageUrls.add(url);
        mNames.add(course);

    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init RecyclerView");
        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        RecyclerViewAdapter adapter= new RecyclerViewAdapter(this, mNames, mImageUrls);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
        return super.onOptionsItemSelected(item);
    }

}
