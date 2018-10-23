package com.example.mid_year_registration;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.hitomi.cmlibrary.OnMenuStatusChangeListener;

import static com.example.mid_year_registration.LoginActivity.isConnectingToInternet;

public class StudentMenuActivity extends AppCompatActivity {

    String arrayName[] = { "Upload Request", "", "Reset Password","Logout", "View Request"};
    ConstraintLayout mConstraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_menu_activity);
        mConstraintLayout = findViewById(R.id.studentmenu);

        getSupportActionBar().setTitle("Main Menu");

        CircleMenu circleMenu = findViewById(R.id.circle_menu);
        circleMenu.setMainMenu(Color.parseColor("#CDCDCD"), R.drawable.view_menu,R.drawable.close_menu)
                .addSubMenu(Color.parseColor("#258CFF"), R.drawable.upload_form)
                .addSubMenu(Color.parseColor("#30A400"), R.drawable.view_refresh)
                .addSubMenu(Color.parseColor("#FF4B32"), R.drawable.resetp)
                .addSubMenu(Color.parseColor("#8A39FF"), R.drawable.if_logout_63128)
                .addSubMenu(Color.parseColor("#FF6A00"), R.drawable.view_request1)
                .setOnMenuSelectedListener(new OnMenuSelectedListener() {

                    @Override
                    public void onMenuSelected(int index) {

                        if(arrayName[index].contains("Upload Request")){
                            Toast.makeText(StudentMenuActivity.this, "You selected "+arrayName[index], Toast.LENGTH_SHORT).show();
                            Intent activity = new Intent(StudentMenuActivity.this, StudentUpload.class);
                            startActivity(activity);
                        }
                        if(arrayName[index].contains("View Request")){ // it should go to a page where the student will view his submitted request
                            if(  isConnectingToInternet(StudentMenuActivity.this) == false) {
                                Snackbar.make(mConstraintLayout, "No Internet Connection ", Snackbar.LENGTH_LONG).show();
                                //mProgressDialog.dismiss();
                                return;

                            }

                            Toast.makeText(StudentMenuActivity.this, "You selected "+arrayName[index], Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(StudentMenuActivity.this, StudentConcessionsActivity.class);
                            startActivity(intent);
                        }
                        //allow user to add courses which they're coordinating
                        if(arrayName[index].contains("")){
                            Toast.makeText(StudentMenuActivity.this, "You selected "+arrayName[index], Toast.LENGTH_SHORT).show();
                            //Intent activity = new Intent(StudentMenuActivity.this, AddCoursesActivity.class);
                            //startActivity(activity);
                        }
                        if(arrayName[index].contains("Reset Password")){
                            Toast.makeText(StudentMenuActivity.this, "You selected "+arrayName[index], Toast.LENGTH_SHORT).show();
                            Intent activity = new Intent(StudentMenuActivity.this, PasswordResetActivity.class);
                            startActivity(activity);
                        }
                        if(arrayName[index].contains("Logout")){
                            Toast.makeText(StudentMenuActivity.this, "You selected "+arrayName[index], Toast.LENGTH_SHORT).show();
                            Intent activity = new Intent(StudentMenuActivity.this, LoginActivity.class);
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
