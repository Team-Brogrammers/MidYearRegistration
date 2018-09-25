package com.example.mid_year_registration;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class CoordinatorUpload extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coordinator_upload_activity);
        getSupportActionBar().setTitle("Upload Form");
    }
}
