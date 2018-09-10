package com.example.mid_year_registration;

import android.support.test.filters.SmallTest;
import android.test.ActivityInstrumentationTestCase2;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class StudentUploadTest extends ActivityInstrumentationTestCase2<StudentUpload> {
    StudentUpload activity;

    public StudentUploadTest() {
        super(StudentUpload.class);
    }

    @Override
    protected void setUp() throws Exception{
        super.setUp();
        activity = getActivity();
    }

    @SmallTest
    public void testVisibility(){
        onView(withId(R.id.formImageView)).check(matches(isDisplayed()));
        onView(withId(R.id.pdfView)).check(matches(isDisplayed()));

        onView(withId(R.id.stdNoEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.etCourse)).check(matches(isDisplayed()));

        onView(withId(R.id.btnAddImage)).check(matches(isDisplayed()));
        onView(withId(R.id.btnConvert)).check(matches(isDisplayed()));
        //onView(withId(R.id.btnSubmit)).check(matches(isDisplayed()));

    }

}
