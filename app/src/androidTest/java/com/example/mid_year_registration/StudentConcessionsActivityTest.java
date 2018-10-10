package com.example.mid_year_registration;

import android.support.test.filters.SmallTest;
import android.test.ActivityInstrumentationTestCase2;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class StudentConcessionsActivityTest extends ActivityInstrumentationTestCase2<StudentConcessionsActivity> {

    StudentConcessionsActivity activity;

    public StudentConcessionsActivityTest(){
        super(StudentConcessionsActivity.class);
    }

    @Override
    protected void setUp() throws Exception{
        super.setUp();
        activity = getActivity();
    }

    @SmallTest
    public void testVisibility(){
        // dismiss the progress dialog
        if(activity.getmProgressDialog().isShowing()){
            activity.getmProgressDialog().dismiss();
        }

        onView(withId(R.id.studentRecyclerView)).check(matches(isDisplayed()));

    }

}
