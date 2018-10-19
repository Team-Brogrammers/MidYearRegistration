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

import java.util.ArrayList;

public class AddCoursesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // initialize variables
    private static final String ANONYMOUS = "anonymous";
    private ProgressDialog mProgressDialog;
    private ConstraintLayout constraintLayout;
    private Spinner c0;
    private Spinner c1;
    private Spinner c2;
    private Spinner c3;
    private Spinner c4;
    private Spinner c5;
    private Button UpdateButton;

    // initialize Firebase variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseDatabase mFireBaseDatabase;
    private DatabaseReference mUserDatabaseReference;

    String[] cNames = {"BOI", "SBI", "HDFC", "PNB", "OBC"};

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
        mUserDatabaseReference = mFireBaseDatabase.getReference().child("Coordinators");

        // get current user
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mUsersKey = mFirebaseAuth.getCurrentUser().getUid();

        // get references
        c0 = findViewById(R.id.spinner0);
        c1 = findViewById(R.id.spinner1);
        c2 = findViewById(R.id.spinner2);
        c3 = findViewById(R.id.spinner3);
        c4 = findViewById(R.id.spinner4);
        c5 = findViewById(R.id.spinner5);
        constraintLayout = findViewById(R.id.addCoursesConstraintLayout);
        UpdateButton = findViewById(R.id.updateBotton);

        ArrayAdapter<CharSequence> courses0;
        ArrayAdapter<CharSequence> courses1;
        ArrayAdapter<CharSequence> courses2;
        ArrayAdapter<CharSequence> courses3;
        ArrayAdapter<CharSequence> courses4;
        ArrayAdapter<CharSequence> courses5;

        //ArrayList<ArrayAdapter> mylist = new ArrayList<ArrayAdapter>();

        /*final String[] course0Arr = new String[10];
        final String[] course1Arr = new String[10];
        final String[] course2Arr = new String[10];
        final String[] course3Arr = new String[10];
        final String[] course4Arr = new String[10];
        final String[] course5Arr = new String[10];
        course0Arr[0] = "No selection";
        course1Arr[0] = "No selection";
        course2Arr[0] = "No selection";
        course3Arr[0] = "No selection";
        course4Arr[0] = "No selection";
        course5Arr[0] = "No selection";*/
        String[] course0Arr = {"select a 3rd year maths course", "remove selection", "MATH3001", "MATH3002", "MATH3034", "MATH3003", "MATH3035"};
        String[] course1Arr = {"select a 3rd year coms course", "remove selection", "COMS3003", "COMS3009", "COMS3011", "COMS3002", "COMS3000"};
        String[] course2Arr = {"select a 3rd year appm course", "remove selection", "MATH3001", "MATH3002", "MATH3034", "MATH3003", "MATH3035"};
        String[] course3Arr = {"select a 2nd year maths course", "remove selection", "COMS3003", "COMS3009", "COMS3011", "COMS3002", "COMS3000"};
        String[] course4Arr = {"select a 2nd year coms course", "remove selection", "COMS3003", "COMS3009", "COMS3011", "COMS3002", "COMS3000"};
        String[] course5Arr = {"select a 2nd year maths course", "remove selection", "COMS3003", "COMS3009", "COMS3011", "COMS3002", "COMS3000"};

        courses0 = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, course0Arr);
        courses0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        c0.setAdapter(courses0);

        courses1 = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, course1Arr);
        courses1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        c1.setAdapter(courses1);

        courses2 = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, course2Arr);
        courses2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        c2.setAdapter(courses2);

        courses3 = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, course3Arr);
        courses3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        c3.setAdapter(courses3);

        courses4 = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, course4Arr);
        courses4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        c4.setAdapter(courses4);

        courses5 = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, course5Arr);
        courses5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        c5.setAdapter(courses5);

        // initialize progressbar
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Loading updates");
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        //mProgressDialog.show();

        // Display current user profile details
        /*mUserDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot child : dataSnapshot.getChildren()) {

                    if(child.getValue().toString().contains("COMS2")){
                        if(child.child("Coordinator_uid").equals(mUsersKey)) {
                            course0Arr[n0] = child.getValue().toString();
                            n0++;
                        }
                    }else if(child.getValue().toString().contains("COMS3")){
                        if(child.child("Coordinator_uid").equals(mUsersKey)) {
                            course1Arr[n1] = child.getValue().toString();
                            n1++;
                        }
                    }else if(child.getValue().toString().contains("APPM")){
                            if(child.child("Coordinator_uid").equals(mUsersKey)) {
                                course2Arr[n2] = child.getValue().toString();
                                n2++;
                            }
                    }else if(child.getValue().toString().contains("MATH2")){
                            if(child.child("Coordinator_uid").equals(mUsersKey)) {
                                course3Arr[n3] = child.getValue().toString();
                                n3++;
                            }
                    }else if(child.getValue().toString().contains("MATH3")){
                            if(child.child("Coordinator_uid").equals(mUsersKey)) {
                                course1Arr[n3] = child.getValue().toString();
                                n3++;
                            }
                    }
                }

                /*String user_course1 = (String) dataSnapshot.child("course1").getValue();
                String user_course2 = (String) dataSnapshot.child("course2").getValue();
                String user_course3 = (String) dataSnapshot.child("course3").getValue();
                String user_email = mFirebaseAuth.getCurrentUser().getEmail();

                getCourse1.setText(user_course1);
                getEmail.setText(user_email);
                getCourse2.setText(user_course2);
                getCourse3.setText(user_course3);*/

                //mProgressDialog.dismiss();
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//            });
//


        /*String selectedc0 = c0.getSelectedItem().toString();
        String selectedc1 = c1.getSelectedItem().toString();
        String selectedc2 = c2.getSelectedItem().toString();
        String selectedc3 = c3.getSelectedItem().toString();
        String selectedc4 = c4.getSelectedItem().toString();
        String selectedc5 = c5.getSelectedItem().toString();*/

       // Toast.makeText(getApplicationContext(), "selected "+selectedc0, Toast.LENGTH_LONG).show();

        UpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // update account changes
                updateAccount();
            }
        });



    }

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
    
    private void updateAccount() {
        assert mFirebaseAuth.getCurrentUser() != null;
        String selectedc0 = c0.getSelectedItem().toString().trim();
        String selectedc1 = c1.getSelectedItem().toString().trim();
        String selectedc2 = c2.getSelectedItem().toString().trim();
        String selectedc3 = c3.getSelectedItem().toString().trim();
        String selectedc4 = c4.getSelectedItem().toString().trim();
        String selectedc5 = c5.getSelectedItem().toString().trim();

        final String user_id = mFirebaseAuth.getCurrentUser().getUid();

        if((selectedc0.isEmpty() && selectedc1.isEmpty() && selectedc3.isEmpty() &&
                selectedc4.isEmpty() && selectedc5.isEmpty())){
            Snackbar.make(constraintLayout, "Provide at least one course that you're coordinating!", Snackbar.LENGTH_LONG ).show();
            return;
        }

        if(!TextUtils.isEmpty(mAuthor) && (mFirebaseAuth.getCurrentUser() != null)){
            mProgressDialog.setTitle("Updating Profile");
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();

            mUserDatabaseReference.child(user_id).child("coordinate0").setValue(selectedc0);
            mUserDatabaseReference.child(user_id).child("coordinate1").setValue(selectedc1);
            mUserDatabaseReference.child(user_id).child("coordinate2").setValue(selectedc2);
            mUserDatabaseReference.child(user_id).child("coordinate3").setValue(selectedc3);
            mUserDatabaseReference.child(user_id).child("coordinate4").setValue(selectedc4);
            mUserDatabaseReference.child(user_id).child("coordinate5").setValue(selectedc5);

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
