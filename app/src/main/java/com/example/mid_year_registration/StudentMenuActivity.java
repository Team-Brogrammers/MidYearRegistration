package com.example.mid_year_registration;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.hitomi.cmlibrary.OnMenuStatusChangeListener;

public class StudentMenuActivity extends AppCompatActivity {

    String arrayName[] = { "Upload Request", "View Request", "Veiw Request 1","Veiw Request 2", "Veiw Request 3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_menu_activity);

        getSupportActionBar().setTitle("Mid Year Registration");

        CircleMenu circleMenu = findViewById(R.id.circle_menu);
        circleMenu.setMainMenu(Color.parseColor("#CDCDCD"), R.drawable.view_menu,R.drawable.close_menu)
                .addSubMenu(Color.parseColor("#258CFF"), R.drawable.upload_request)
                .addSubMenu(Color.parseColor("#30A400"), R.drawable.view_request)
                .addSubMenu(Color.parseColor("#FF4B32"), R.drawable.view_request1)
                .addSubMenu(Color.parseColor("#8A39FF"), R.drawable.view_request2)
                .addSubMenu(Color.parseColor("#FF6A00"), R.drawable.view_request3)
                .setOnMenuSelectedListener(new OnMenuSelectedListener() {

                    @Override
                    public void onMenuSelected(int index) {
                        Toast.makeText(StudentMenuActivity.this, "You selected "+arrayName[index], Toast.LENGTH_SHORT).show();

                        if(arrayName[index].contains("Upload Request")){
                            Intent activity = new Intent(StudentMenuActivity.this, StudentUpload.class);
                            startActivity(activity);
                        }
                    }

                }).setOnMenuStatusChangeListener(new OnMenuStatusChangeListener() {

            @Override
            public void onMenuOpened() {}

            @Override
            public void onMenuClosed() {}

        });


    }

}
