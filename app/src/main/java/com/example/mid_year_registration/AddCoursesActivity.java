package com.example.mid_year_registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.UploadTask;

public class AddCoursesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    // initialize variables
    private static final String ANONYMOUS = "anonymous";
    private ProgressDialog mProgressDialog;
    private ConstraintLayout constraintLayout;
    private TextView getEmail;
    private EditText getCourse1;
    private EditText getCourse2;
    private EditText getCourse3;
    private Button UpdateButton;

    // initialize Firebase variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseDatabase mFireBaseDatabase;
    private DatabaseReference mUserDatabaseReference;

    String[] bankNames={"BOI","SBI","HDFC","PNB","OBC"};

    private String mAuthor;
    public String mUsersKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_courses_activity);

        // set toolbar title
        getSupportActionBar().setTitle("Add Edit View Courses");
        /*Back button*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuthor = ANONYMOUS;

        // Initialize Firebase components
        mFireBaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mUserDatabaseReference = mFireBaseDatabase.getReference().child("users");

        // get current user
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mUsersKey = mFirebaseAuth.getCurrentUser().getUid();

        // get references
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spin0 = (Spinner) findViewById(R.id.spinner0);
        spin0.setOnItemSelectedListener(this);

        Spinner spin1 = (Spinner) findViewById(R.id.spinner0);
        spin1.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,bankNames);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin0.setAdapter(aa);
        spin1.setAdapter(aa);
    }


        /*getCourse1 = findViewById(R.id.course1EditText);
        getCourse2 = findViewById(R.id.course2EditText);
        getCourse3 = findViewById(R.id.course3EditText);
        getEmail = findViewById(R.id.addEmailTextInputLayout);

        constraintLayout = findViewById(R.id.addCoursesConstraintLayout);*/

        // initialize progressbar
        /*mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Loading updates");
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();*/

        // Display current user profile details
       /* mUserDatabaseReference.child(mUsersKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String user_course1 = (String) dataSnapshot.child("course1").getValue();
                String user_course2 = (String) dataSnapshot.child("course2").getValue();
                String user_course3 = (String) dataSnapshot.child("course3").getValue();
                String user_email = mFirebaseAuth.getCurrentUser().getEmail();

                getCourse1.setText(user_course1);
                getEmail.setText(user_email);
                getCourse2.setText(user_course2);
                getCourse3.setText(user_course3);

                mProgressDialog.dismiss();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });*/

        /*UpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // update account changes
                //updateAccount();
            }
        });

    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            Intent intent = new Intent(getApplicationContext(),CoordinatorMenuActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //
    private void updateAccount() {
        assert mFirebaseAuth.getCurrentUser() != null;
        final String user_id = mFirebaseAuth.getCurrentUser().getUid();
        final String course1 = getCourse1.getText().toString();
        final String course2 = getCourse2.getText().toString();
        final String course3 = getCourse3.getText().toString();
        String email = mFirebaseAuth.getCurrentUser().getEmail();

        if(email.isEmpty()){
            getEmail.setError("Provide email address");
            return;
        }
        if((course1.isEmpty() && course2.isEmpty() && course3.isEmpty())){
            Snackbar.make(constraintLayout, "Provide at least one course that you're coordinating!", Snackbar.LENGTH_LONG ).show();
            return;
        }

        if(!TextUtils.isEmpty(mAuthor) && !TextUtils.isEmpty(email)){
            mProgressDialog.setTitle("Setting Up Profile");
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();

            mUserDatabaseReference.child(user_id).child("email").setValue(email);
            mUserDatabaseReference.child(user_id).child("course1").setValue(course1);
            mUserDatabaseReference.child(user_id).child("course2").setValue(course2);
            mUserDatabaseReference.child(user_id).child("course3").setValue(course3);

            mProgressDialog.dismiss();
            Toast.makeText(AddCoursesActivity.this, "Profile successfully updated", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(AddCoursesActivity.this, CoordinatorMenuActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

        }else{
            mProgressDialog.dismiss();
            Toast.makeText(AddCoursesActivity.this, "Failed to update your profile, please try again.", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
