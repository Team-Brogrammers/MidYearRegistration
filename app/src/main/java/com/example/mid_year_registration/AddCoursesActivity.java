package com.example.mid_year_registration;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class AddCoursesActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_courses_activity);

        getSupportActionBar().setTitle("Add Edit View Courses");
    }
}
