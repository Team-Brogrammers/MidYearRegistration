package com.example.mid_year_registration;


import android.content.Intent;
import android.support.test.filters.SmallTest;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class UploadTest extends ActivityInstrumentationTestCase2<UploadActivity> {
    UploadActivity activity;

    public UploadTest() {
        super(UploadActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Intent intent = new Intent();
        intent.putExtra("filename", "test.pdf");
        intent.putExtra("studentNumber", "1234567");
        intent.putExtra("courseCode", "COMS1001");
        setActivityIntent(intent);
        activity = getActivity();
    }

    @SmallTest
    public void testVisibility() {
        /*I am only Testing the visibility here.*/
        onView(withId(R.id.textView2)).check(matches(isDisplayed()));
        onView(withId(R.id.attachmentFab)).check(matches(isDisplayed()));
        onView(withId(R.id.uploadFab)).check(matches(isDisplayed()));
    }

    @SmallTest
    public void testButtonsClickable(){
        onView(withId(R.id.attachmentFab)).check(matches(isClickable()));
        onView(withId(R.id.uploadFab)).check(matches(isClickable()));
    }

/*
    @Test
    public void simpleCircuitTest() {
        onView(withId(R.id.attachmentFab)).perform(click());
        onView(withId(R.id.updateBotton)).perform(click());
    }
*/



}