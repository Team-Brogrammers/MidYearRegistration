package com.example.mid_year_registration;

import android.content.Intent;
import android.support.test.filters.SmallTest;
import android.test.ActivityInstrumentationTestCase2;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class ViewConcessionActivityTest extends ActivityInstrumentationTestCase2<ViewConcessionActivity> {
    ViewConcessionActivity activity;
    public ViewConcessionActivityTest() {
        super(ViewConcessionActivity.class);
    }

    @Override
    protected void setUp() throws Exception{
        super.setUp();
        Intent intent = new Intent();
        intent.putExtra("name", "1425376_COMS2001__2018-09-20");
        intent.putExtra("studentNo","1425376");
        intent.putExtra("course","COMS2001");
        setActivityIntent(intent);
        activity = getActivity();
    }

    @SmallTest
    public void testVisibility(){
        onView(withId(R.id.tvConcessionCourse)).check(matches(isDisplayed()));
        onView(withId(R.id.tvConcessionStudent)).check(matches(isDisplayed()));
        onView(withId(R.id.tvConcessionCourseVal)).check(matches(isDisplayed()));
        onView(withId(R.id.tvConcessionStudentVal)).check(matches(isDisplayed()));
        onView(withId(R.id.CoordPdfView)).check(matches(isDisplayed()));
    }

}
