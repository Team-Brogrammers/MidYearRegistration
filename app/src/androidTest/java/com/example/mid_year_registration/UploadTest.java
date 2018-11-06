//package com.example.mid_year_registration;
//
//
//import android.app.Activity;
//import android.app.Instrumentation;
//import android.content.ComponentName;
//import android.content.Intent;
//import android.support.test.InstrumentationRegistry;
//import android.support.test.espresso.intent.Intents;
//import android.support.test.filters.SmallTest;
//import android.test.ActivityInstrumentationTestCase2;
//
//import org.junit.Test;
//
//import static android.support.test.InstrumentationRegistry.getTargetContext;
//import static android.support.test.espresso.Espresso.onView;
//import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
//import static android.support.test.espresso.action.ViewActions.click;
//import static android.support.test.espresso.assertion.ViewAssertions.matches;
//import static android.support.test.espresso.intent.Intents.intended;
//import static android.support.test.espresso.intent.Intents.intending;
//import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
//import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
//import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
//import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
//import static android.support.test.espresso.matcher.ViewMatchers.withId;
//import static android.support.test.espresso.matcher.ViewMatchers.withText;
//
//public class UploadTest extends ActivityInstrumentationTestCase2<UploadActivity> {
//    UploadActivity activity;
//
//    public UploadTest() {
//        super(UploadActivity.class);
//    }
//
//    @Override
//    protected void setUp() throws Exception {
//        super.setUp();
//        Intent intent = new Intent();
//        intent.putExtra("filename", "test.pdf");
//        intent.putExtra("studentNumber", "1234567");
//        intent.putExtra("courseCode", "COMS1001");
//        setActivityIntent(intent);
//        activity = getActivity();
//    }
//
////    @SmallTest
////    public void testVisibility() {
////        /*I am only Testing the visibility here.*/
////        onView(withId(R.id.textView2)).check(matches(isDisplayed()));
////        onView(withId(R.id.attachmentFab)).check(matches(isDisplayed()));
////        onView(withId(R.id.uploadFab)).check(matches(isDisplayed()));
////    }
//
////    @SmallTest
////    public void testButtonsClickable(){
////        onView(withId(R.id.attachmentFab)).check(matches(isClickable()));
////        onView(withId(R.id.uploadFab)).check(matches(isClickable()));
////    }
//
////    @Test
////    @SmallTest
////    public void testBackButton(){
////
////        Intents.init();
////        Intent resultData = new Intent();
////        resultData.putExtra("filename", "test.pdf");
////        resultData.putExtra("studentNumber", "1234567");
////        resultData.putExtra("courseCode", "COMS1001");
////        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
////        intending(toPackage("com.example.mid_year_registration")).respondWith(result);
////        onView(withContentDescription("Navigate up")).perform(click());
////        intended(hasComponent(new ComponentName(getTargetContext(), StudentUpload.class)));
////        Intents.release();
////    }
//
////    @Test
////    @SmallTest
////    public void testLogoutButton(){
////
////        Intents.init();
////        Intent resultData = new Intent();
////        resultData.putExtra("filename", "test.pdf");
////        resultData.putExtra("studentNumber", "1234567");
////        resultData.putExtra("courseCode", "COMS1001");
////        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
////        intending(toPackage("com.example.mid_year_registration")).respondWith(result);
////        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
////        onView(withText(R.string.logout)).perform(click());
////        intended(hasComponent(new ComponentName(getTargetContext(), LoginActivity.class)));
////        Intents.release();
////    }
//
//}