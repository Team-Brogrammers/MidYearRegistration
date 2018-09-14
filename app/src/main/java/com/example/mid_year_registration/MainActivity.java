package com.example.mid_year_registration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Button button = (Button)findViewById(R.id.view_courses);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this,view_module_courses.class);

                startActivity(intent);
                finish();
            }
        });
    }



    public void OnClick(View view){
        Intent intent = new Intent(MainActivity.this,view_module_courses.class);

        startActivity(intent);
        finish();
    }

}
