//package com.example.mid_year_registration;
//
//import android.support.test.filters.SmallTest;
//import android.test.ActivityInstrumentationTestCase2;
//
//import static android.support.test.espresso.Espresso.onView;
//import static android.support.test.espresso.assertion.ViewAssertions.matches;
//import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static android.support.test.espresso.matcher.ViewMatchers.withId;
//
//public class UploadActivityTest extends ActivityInstrumentationTestCase2<UploadActivity> {
//    UploadActivity activity;
//
//    public UploadActivityTest() {
//        super(UploadActivity.class);
//    }
//
//    @Override
//    protected void setUp() throws Exception{
//        super.setUp();
//        activity = getActivity();
//    }
//
//    @SmallTest
//    public void testVisibility(){
//
//        onView(withId(R.id.PdfView)).check(matches(isDisplayed()));
//
//        onView(withId(R.id.pdfNameTextView)).check(matches(isDisplayed()));
//
//
//        onView(withId(R.id.attachmentFab)).check(matches(isDisplayed()));
//        onView(withId(R.id.uploadFab)).check(matches(isDisplayed()));
//
//
//    }
//
//}
