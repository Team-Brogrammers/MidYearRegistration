package com.example.mid_year_registration;

import android.content.Intent;
import android.support.test.filters.SmallTest;
import android.test.ActivityInstrumentationTestCase2;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class CoordinatorUploadPdfActivityTest extends ActivityInstrumentationTestCase2<CoordinatorUploadPdfActivity> {
    CoordinatorUploadPdfActivity activity;

    public CoordinatorUploadPdfActivityTest() {
        super(CoordinatorUploadPdfActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Intent intent = new Intent();
        intent.putExtra("filename", "test.pdf");
        intent.putExtra("studentNumber", "1234567");
        intent.putExtra("courseCode", "COMS2001");
        setActivityIntent(intent);
        activity = getActivity();
    }

    @SmallTest
    public void testVisibility(){
        onView(withId(R.id.PdfView)).check(matches(isDisplayed()));
        onView(withId(R.id.textView2)).check(matches(isDisplayed()));
        onView(withId(R.id.attachmentFab)).check(matches(isDisplayed()));
    }

    @SmallTest
    public void testButtonsClickable(){
        onView(withId(R.id.attachmentFab)).check(matches(isClickable()));
        onView(withId(R.id.attachmentFab)).check(matches(isClickable()));
    }
}
