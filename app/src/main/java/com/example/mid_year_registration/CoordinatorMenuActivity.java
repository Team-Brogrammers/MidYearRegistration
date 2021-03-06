package com.example.mid_year_registration;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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

public class CoordinatorMenuActivity extends AppCompatActivity {

    String arrayName[] = {"Upload Student Form", "Add Courses", "Reset Password","Logout", "View Student Request"};
    ConstraintLayout mConstraintLayout;
    boolean b1 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coordinator_menu_activity);
        mConstraintLayout = findViewById(R.id.cordinatormenu);

        getSupportActionBar().setTitle("Main Menu");

        CircleMenu circleMenu = findViewById(R.id.circle_menu2);
        circleMenu.setMainMenu(Color.parseColor("#CDCDCD"), R.drawable.view_menu,R.drawable.close_menu)
                .addSubMenu(Color.parseColor("#258CFF"), R.drawable.uploadsicon)
                .addSubMenu(Color.parseColor("#30A400"), R.drawable.courses)
                .addSubMenu(Color.parseColor("#FF4B32"), R.drawable.resetp)
                .addSubMenu(Color.parseColor("#8A39FF"), R.drawable.if_exit_28363)
                .addSubMenu(Color.parseColor("#FF6A00"), R.drawable.view_request1)
                .setOnMenuSelectedListener(new OnMenuSelectedListener() {

                    @Override
                    public void onMenuSelected(int index) {

                        if(arrayName[index].contains("Upload Student Form")){
                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CoordinatorMenuActivity.this);
                            builder.setMessage("Upload a student's form?").setCancelable(false);
                            builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent activity = new Intent(CoordinatorMenuActivity.this, CoordinatorUploadActivity.class);
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
                        if(arrayName[index].contains("View Student Request")){
                            if( isConnectingToInternet(CoordinatorMenuActivity.this) == false) {
                                Snackbar.make(mConstraintLayout, "No Internet Connection ", Snackbar.LENGTH_LONG).show();
                                return;
                            }

                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CoordinatorMenuActivity.this);
                            builder.setMessage("View Student Request?").setCancelable(false);
                            builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Toast.makeText(CoordinatorMenuActivity.this, "You selected View Student Request", Toast.LENGTH_SHORT).show();
                                    Intent activity = new Intent(CoordinatorMenuActivity.this, MainActivity.class);
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
                        if(arrayName[index].contains("Add Courses")){
                            if( isConnectingToInternet(CoordinatorMenuActivity.this) == false) {
                                Snackbar.make(mConstraintLayout, "No Internet Connection ", Snackbar.LENGTH_LONG).show();
                                return;
                            }

                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CoordinatorMenuActivity.this);
                            builder.setMessage("Add/View Courses?").setCancelable(false);
                            builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent activity = new Intent(CoordinatorMenuActivity.this, AddCoursesActivity.class);
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
                        if(arrayName[index].contains("Reset Password")){
                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CoordinatorMenuActivity.this);
                            builder.setMessage("Do you want to reset your password?").setCancelable(false);
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent activity = new Intent(CoordinatorMenuActivity.this, PasswordResetActivity.class);
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
                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CoordinatorMenuActivity.this);
                            builder.setMessage("Are you sure you want to logout?").setCancelable(false);
                            builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent activity = new Intent(CoordinatorMenuActivity.this, LoginActivity.class);
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
            public void onMenuOpened() {

            }

            @Override
            public void onMenuClosed() {

            }

        });

    }


    @Override
    public void onBackPressed() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CoordinatorMenuActivity.this);
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

    public void onSelectedOption() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CoordinatorMenuActivity.this);
        builder.setMessage("Sure to exit the App?").setCancelable(false);
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

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
