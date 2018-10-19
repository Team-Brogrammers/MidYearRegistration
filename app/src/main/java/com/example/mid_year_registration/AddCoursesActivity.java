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
import java.util.Arrays;

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
    private String mAuthor;
    public String mUsersKey;

    public static final String[] course0Arr = {"No selection", "COMS2002", "COMS2013", "COMS2014", "COMS2015"};
    public static final String[] course1Arr = {"No selection", "COMS3002", "COMS3003", "COMS3005",
                                               "COMS3006", "COMS3007", "COMS3008", "COMS3009"};
    public static final String[] course2Arr = {"No selection", "MATH1034", "MATH1036"};
    public static final String[] course3Arr = {"No selection", "MATH2001", "MATH2007", "MATH2016",
                                               "MATH2015", "MATH2019", "MATH2003"};
    public static final String[] course4Arr = {"No selection", "MATH3001", "MATH3003", "MATH3004",
                                               "MATH3006", "MATH3009", "MATH3010", "MATH3034", "MATH3031", "MATH3032"};
    public static final String[] course5Arr = {"No selection", "APPM1006", "APPM2007", "APPM3017"};

    public static final String[] allCourses = {
            "APPM1006", "APPM2007", "APPM3017",
            "COMS1015", "COMS1018", "COMS1017", "COMS1016",
            "COMS2002", "COMS2013", "COMS2014", "COMS2015",
            "COMS3002", "COMS3003", "COMS3005", "COMS3006", "COMS3007", "COMS3008", "COMS3009",
            "MATH1034", "MATH1036", "MATH2001", "MATH2007", "MATH2016", "MATH2015", "MATH2019", "MATH2003",
            "MATH3001", "MATH3003", "MATH3004", "MATH3006", "MATH3009", "MATH3010", "MATH3034", "MATH3031", "MATH3032",
    };

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

        courses0 = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, course0Arr);
        courses0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        c0.setAdapter(courses0);

        courses1 = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, course0Arr);
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
        mProgressDialog.show();

        // Display current user profile details
        mUserDatabaseReference.child(mUsersKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String user_course0 = (String) dataSnapshot.child("coordinate0").getValue();
                String user_course1 = (String) dataSnapshot.child("coordinate1").getValue();
                String user_course2 = (String) dataSnapshot.child("coordinate2").getValue();
                String user_course3 = (String) dataSnapshot.child("coordinate3").getValue();
                String user_course4 = (String) dataSnapshot.child("coordinate4").getValue();
                String user_course5 = (String) dataSnapshot.child("coordinate5").getValue();

                //cast to an ArrayAdapter
                ArrayAdapter myAdap0 = (ArrayAdapter) c0.getAdapter();
                ArrayAdapter myAdap1 = (ArrayAdapter) c1.getAdapter();
                ArrayAdapter myAdap2 = (ArrayAdapter) c2.getAdapter();
                ArrayAdapter myAdap3 = (ArrayAdapter) c3.getAdapter();
                ArrayAdapter myAdap4 = (ArrayAdapter) c4.getAdapter();
                ArrayAdapter myAdap5 = (ArrayAdapter) c5.getAdapter();

                // get position in the adapter
                int spinnerPosition0 = myAdap0.getPosition(user_course0);
                int spinnerPosition1 = myAdap1.getPosition(user_course1);
                int spinnerPosition2 = myAdap2.getPosition(user_course2);
                int spinnerPosition3 = myAdap3.getPosition(user_course3);
                int spinnerPosition4 = myAdap4.getPosition(user_course4);
                int spinnerPosition5 = myAdap5.getPosition(user_course5);

                //set the default according to value
                c0.setSelection(spinnerPosition0);
                c1.setSelection(spinnerPosition1);
                c2.setSelection(spinnerPosition2);
                c3.setSelection(spinnerPosition3);
                c4.setSelection(spinnerPosition4);
                c5.setSelection(spinnerPosition5);

                mProgressDialog.dismiss();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }});

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

        ArrayList<String> myArr = new ArrayList<String>();
        ArrayList<String> Arr = new ArrayList<String>();
        Arr.addAll(Arrays.asList(selectedc0, selectedc1, selectedc2, selectedc3, selectedc4, selectedc5));

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

            for(int i = 0; i < Arr.size(); i++) {
                if (!myArr.contains(Arr.get(i))) {
                    myArr.add(Arr.get(i));
                }else {
                    mProgressDialog.dismiss();
                    Snackbar.make(constraintLayout, "Error! You selected a course more than once!", Snackbar.LENGTH_LONG ).show();
                    return;
                }
            }

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
