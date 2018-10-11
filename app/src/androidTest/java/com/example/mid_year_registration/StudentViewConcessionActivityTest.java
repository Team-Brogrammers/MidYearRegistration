package com.example.mid_year_registration;

import android.content.Intent;
import android.support.test.filters.SmallTest;
import android.test.ActivityInstrumentationTestCase2;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class StudentViewConcessionActivityTest extends ActivityInstrumentationTestCase2<StudentViewConcessionActivity> {
    StudentViewConcessionActivity activity;

    public StudentViewConcessionActivityTest(){
        super(StudentViewConcessionActivity.class);
    }

    @Override
    protected void setUp() throws Exception{
        super.setUp();
        Intent intent = new Intent();
        intent.putExtra("name", "9876543_COMS1099_2018-09-27.pdf");
        intent.putExtra("studentNo","9876543");
        intent.putExtra("course","COMS1099");
        intent.putExtra("comment", "No Comment");
        setActivityIntent(intent);
        activity = getActivity();
    }

    @SmallTest
    public void testVisibility(){
        // dismiss the progress dialog
        if(activity.getmProgressDialog().isShowing()){
            activity.getmProgressDialog().dismiss();
        }

        onView(withId(R.id.tvStudentConcessionCourse)).check(matches(isDisplayed()));
        onView(withId(R.id.tvStudentConcessionCourseVal)).check(matches(isDisplayed()));
        onView(withId(R.id.tvCommentView)).check(matches(isDisplayed()));
        onView(withId(R.id.StudentPdfView)).check(matches(isDisplayed()));

    }

}
