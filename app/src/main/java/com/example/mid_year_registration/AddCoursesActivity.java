package com.example.mid_year_registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class AddCoursesActivity extends AppCompatActivity{

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
        getCourse1 = findViewById(R.id.course1EditText);
        getCourse2 = findViewById(R.id.course2EditText);
        getCourse3 = findViewById(R.id.course3EditText);
        getEmail = findViewById(R.id.addEmailTextInputLayout);
        UpdateButton = findViewById(R.id.updateBotton);
        constraintLayout = findViewById(R.id.addCoursesConstraintLayout);

        // initialize progressbar
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Logging In");
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

        // Display current user profile details
        mUserDatabaseReference.child(mUsersKey).addValueEventListener(new ValueEventListener() {
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
        });

        UpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // update account changes
                updateAccount();
            }
        });

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
}
