package com.example.mid_year_registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
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

import static com.example.mid_year_registration.LoginActivity.isConnectingToInternet;

//import static com.example.mid_year_registration.LoginActivity.hasInternetAccess;

import java.util.ArrayList;

public class AddCoursesActivity extends AppCompatActivity{

    // initialize variables
    private static final String ANONYMOUS = "anonymous";
    private ProgressDialog mProgressDialog;
    private ConstraintLayout constraintLayout;
    private Button UpdateButton;
    private RecyclerView rvCurrentCourses;
    private RecyclerView rvNewCourses;
    private Spinner  courseSpinner;
    private ArrayAdapter<String> adapter;
    private boolean isSpinnerTouched = false;

    // initialize Firebase variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseDatabase mFireBaseDatabase;
    private DatabaseReference mUserDatabaseReference;

    private String mAuthor;
    public String mUsersKey;
    private ArrayList<Course> currentCourses;
    private ArrayList<Course> oldCourses;
    private ArrayList<Course> courseList;
    private ArrayList<Course> newCourseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_courses_activity);

        // set toolbar title
        getSupportActionBar().setTitle("Add Edit View Courses");
        /*Back button*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuthor = ANONYMOUS;
        currentCourses = new ArrayList<>();
        courseList = new ArrayList<>();
        courseList.add(new Course());
        newCourseList = new ArrayList<>();
        oldCourses = new ArrayList<>();

        // Initialize Firebase components
        mFireBaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mUserDatabaseReference = mFireBaseDatabase.getReference().child("users");

        // get current user
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mUsersKey = mFirebaseAuth.getCurrentUser().getUid();

        // get references
        rvCurrentCourses = findViewById(R.id.rvCurrentCourses);
        rvNewCourses = findViewById(R.id.rvNewCourses);
        UpdateButton = findViewById(R.id.updateBotton);
        constraintLayout = findViewById(R.id.addCoursesConstraintLayout);
        courseSpinner = findViewById(R.id.courseSelectionSpinner2);

        // initialize progressbar
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Logging In");
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

        // Display current user profile details and populate spinner

        mFireBaseDatabase.getReference().child("Courses").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot childSnap : dataSnapshot.getChildren()){
                    Course course = childSnap.getValue(Course.class);
                    course.setCode(childSnap.getKey());

                    // Only show courses without a coordinator
                    if(course.getCoordinator_uid().equals("")){
                        courseList.add(course);
                    }
                    else if(course.getCoordinator_uid().equals(mFirebaseUser.getUid())){
                        currentCourses.add(course);
                        oldCourses.add(course);
                    }
                }
                rvCurrentCourses.setAdapter(new CourseListAdapter(currentCourses));
                initSpinner();
                initCourseListView();
                initNewCourseList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AddCoursesActivity.this, "Failed to download course list", Toast.LENGTH_SHORT).show();
            }
        });

        UpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // update account changes

                if(  isConnectingToInternet(AddCoursesActivity.this) == false) {
                    Snackbar.make(constraintLayout, "No Internet Connection ", Snackbar.LENGTH_LONG).show();

                    return;

                }
                updateAccount();
            }
        });

        courseSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isSpinnerTouched = true;
                return false;
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
        final String user_id = mFirebaseAuth.getCurrentUser().getUid();
        String email = mFirebaseAuth.getCurrentUser().getEmail();
        CourseListAdapter newAdapter = (CourseListAdapter) rvNewCourses.getAdapter();
        CourseListAdapter currAdapter = (CourseListAdapter) rvCurrentCourses.getAdapter();

        ArrayList<Course> newCourses = newAdapter.getCourses();
        if(newCourses.isEmpty() && currentCourses.equals(oldCourses)){
            Toast.makeText(AddCoursesActivity.this,"You haven't made any changes",Toast.LENGTH_SHORT);
        }

        for (Course course : newCourses){

            DatabaseReference ref = mFireBaseDatabase.getReference().child("Courses").child(course.getCode());
            ref.child("Coordinator_uid").setValue(user_id);
        }



        // remove the chosen courses
        for (Course course : oldCourses){
            if(!currentCourses.contains(course)){
                DatabaseReference ref = mFireBaseDatabase.getReference().child("Courses").child(course.getCode());
                ref.child("Coordinator_uid").setValue("");
            }
        }

        Toast.makeText(AddCoursesActivity.this, "Profile Successfully updated",Toast.LENGTH_SHORT);
        Intent intent = new Intent(AddCoursesActivity.this, CoordinatorMenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }

    private void initNewCourseList(){
        RecyclerView newCourseListView = findViewById(R.id.rvNewCourses);
        CourseListAdapter adapter = new CourseListAdapter(newCourseList);
        newCourseListView.setAdapter(adapter);
        newCourseListView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initCourseListView(){
        RecyclerView currentCourseListView = findViewById(R.id.rvCurrentCourses);
        CourseListAdapter adapter = new CourseListAdapter(currentCourses);
        currentCourseListView.setAdapter(adapter);
        currentCourseListView.setLayoutManager(new LinearLayoutManager(this));
        mProgressDialog.dismiss();
    }

    private void initSpinner(){
        ArrayList<String> codes = new ArrayList<>();
        for (Course c : courseList){
            codes.add(c.getCode());
        }
        adapter = new ArrayAdapter<String>(AddCoursesActivity.this, android.R.layout.simple_spinner_item, codes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(adapter);
        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!isSpinnerTouched){
                    return;
                }
                newCourseList.add(courseList.get(i));
                rvNewCourses.setAdapter(new CourseListAdapter(newCourseList));
                courseList.remove(i);
                initSpinner();
                initNewCourseList();
                isSpinnerTouched = false;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
