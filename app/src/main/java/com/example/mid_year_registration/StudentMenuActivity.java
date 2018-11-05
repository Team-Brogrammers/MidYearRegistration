package com.example.mid_year_registration;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.hitomi.cmlibrary.OnMenuStatusChangeListener;

import static com.example.mid_year_registration.LoginActivity.isConnectingToInternet;

public class StudentMenuActivity extends AppCompatActivity {

    String arrayName[] = { "Upload Request", "", "Reset Password","Logout", "View Request"};
    ConstraintLayout mConstraintLayout;
    Bundle bundle1;
    String studentNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_menu_activity);
        mConstraintLayout = findViewById(R.id.studentMenu);
        bundle1 = getIntent().getExtras();
        studentNumber = bundle1.getString("studentNumber");
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
                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(StudentMenuActivity.this);
                            builder.setMessage("Upload a concession request").setCancelable(false);
                            builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent activity = new Intent(StudentMenuActivity.this, StudentUpload.class);
                                    activity.putExtra("studentNumber", studentNumber);
                                    startActivity(activity);
                                }
                            });
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            android.app.AlertDialog alertDialog = builder.create();
                            alertDialog.show();

                        }
                        if(arrayName[index].contains("View Request")){ // it should go to a page where the student will view his submitted request
                            if(  isConnectingToInternet(StudentMenuActivity.this) == false) {
                                Snackbar.make(mConstraintLayout, "No Internet Connection ", Snackbar.LENGTH_LONG).show();
                                //mProgressDialog.dismiss();
                                return;
                            }

                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(StudentMenuActivity.this);
                            builder.setMessage("View your concession request(s)").setCancelable(false);
                            builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent activity = new Intent(StudentMenuActivity.this, StudentConcessionsActivity.class);
                                    startActivity(activity);
                                }
                            });
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            android.app.AlertDialog alertDialog = builder.create();
                            alertDialog.show();

                        }

                        if(arrayName[index].contains("")){
                            if(  isConnectingToInternet(StudentMenuActivity.this) == false) {
                                Snackbar.make(mConstraintLayout, "No Internet Connection ", Snackbar.LENGTH_LONG).show();
                                //mProgressDialog.dismiss();
                                return;
                            }else{
                                Snackbar.make(mConstraintLayout, "You are Connected", Snackbar.LENGTH_LONG).show();
                            }
                            //Toast.makeText(StudentMenuActivity.this, "You selected "+arrayName[index], Toast.LENGTH_SHORT).show();
                            //Intent activity = new Intent(StudentMenuActivity.this, AddCoursesActivity.class);
                            //startActivity(activity);
                        }
                        if(arrayName[index].contains("Reset Password")){
                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(StudentMenuActivity.this);
                            builder.setMessage("Do you want to reset your password?").setCancelable(false);
                            builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent activity = new Intent(StudentMenuActivity.this, PasswordResetActivity.class);
                                    startActivity(activity);
                                }
                            });
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            android.app.AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                        if(arrayName[index].contains("Logout")){
                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(StudentMenuActivity.this);
                            builder.setMessage("Are you sure you want to logout?").setCancelable(false);
                            builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent activity = new Intent(StudentMenuActivity.this, LoginActivity.class);
                                    startActivity(activity);
                                }
                            });
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            android.app.AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }


                    }

                }).setOnMenuStatusChangeListener(new OnMenuStatusChangeListener() {

            @Override
            public void onMenuOpened() {}

            @Override
            public void onMenuClosed() {}

        });


    }

    @Override
    public void onBackPressed() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(StudentMenuActivity.this);
        builder.setMessage("Sure to exit the App?").setCancelable(false);
        builder.setPositiveButton("EXIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("STAY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        android.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

}
