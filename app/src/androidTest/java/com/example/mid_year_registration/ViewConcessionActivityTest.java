package com.example.mid_year_registration;

import android.content.Intent;
import android.support.test.espresso.ViewAction;
import android.support.test.filters.SmallTest;
import android.test.ActivityInstrumentationTestCase2;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class ViewConcessionActivityTest extends ActivityInstrumentationTestCase2<ViewConcessionActivity> {
    ViewConcessionActivity activity;
    public ViewConcessionActivityTest() {
        super(ViewConcessionActivity.class);
    }

    @Override
    protected void setUp() throws Exception{
        super.setUp();
        Intent intent = new Intent();
        intent.putExtra("name", "9876543_COMS1099_2018-09-27.pdf");
        intent.putExtra("studentNo","9876543");
        intent.putExtra("course","COMS1099");
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

//    @SmallTest
//    public void testPdfScroll(){
//       // onView(withId(R.id.CoordPdfView)).perform(swipeUp());
//        //onView(withId(R.id.CoordPdfView)).perform(swipeDown());
//    }

}
