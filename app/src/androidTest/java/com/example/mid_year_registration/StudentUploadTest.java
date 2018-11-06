package com.example.mid_year_registration;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.ComponentName;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.MediumTest;
import android.support.test.filters.SmallTest;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class StudentUploadTest extends ActivityInstrumentationTestCase2<StudentUpload> {
    StudentUpload activity;

    public StudentUploadTest() {
        super(StudentUpload.class);
    }
    @Rule
    public IntentsTestRule<StudentUpload> intentsTestRule =
            new IntentsTestRule<>(StudentUpload.class);

    @Override
    protected void setUp() throws Exception{
        super.setUp();
        activity = getActivity();
    }

    @SmallTest
    public void testVisibility(){
        onView(withId(R.id.formImageView)).check(matches(isDisplayed()));
        onView(withId(R.id.PdfView)).check(matches(isDisplayed()));

        onView(withId(R.id.fileName)).check(matches(isDisplayed()));

        onView(withId(R.id.stdNoEditText)).check(matches(isDisplayed()));
        //onView(withId(R.id.etCourse)).check(matches(isDisplayed()));

        onView(withId(R.id.addImageFab)).check(matches(isDisplayed()));
        onView(withId(R.id.convertImageFab)).check(matches(isDisplayed()));
        onView(withId(R.id.nextFab)).check(matches(isDisplayed()));

    }

    @SmallTest
    public void testButtonsClickable(){
        onView(withId(R.id.addImageFab)).check(matches(isClickable()));
        onView(withId(R.id.convertImageFab)).check(matches(isClickable()));
        onView(withId(R.id.nextFab)).check(matches(isClickable()));
    }

//    @MediumTest
//    public void activityResult_DisplaysImage() {
//        // Build the result to return when the activity is launched.
//
//        Intent resultData = new Intent();
//       // EditText stdNo=;
//        String filename = "1153631_MATH3001";
//        resultData.putExtra("1153631_MATH3001", filename);
//        Instrumentation.ActivityResult result =
//                new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
//
//        // Set up result stubbing when an intent sent to "contacts" is seen.
//        intending(toPackage("com.android.camera")).respondWith(result);
//
//        // User action that results in "contacts" activity being launched.
//        // Launching activity expects phoneNumber to be returned and displayed.
//        //onView(withId(R.id.addImageFab)).perform(click());
//
//        // Assert that the data we set up above is shown.
//        onView(withId(R.id.fileName)).check(matches(withText(filename)));
//        //onView(withId(R.id.fileName)).perform(setName(filename), ;
//    }

    @SmallTest
    public void testValidInputNextButton(){
        Intents.init();
        Intent resultData = new Intent();
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
        intending(toPackage("com.example.mid_year_registration")).respondWith(result);
        onView(withId(R.id.stdNoEditText)).perform(typeText("1234567"), closeSoftKeyboard());
        //onView(withId(R.id.etCourse)).perform(typeText("MATH3001"), closeSoftKeyboard());
        onView(withId(R.id.nextFab)).perform(click());
        intended(hasComponent(new ComponentName(getTargetContext(), UploadActivity.class)));
        Intents.release();
    }

    @Test
    @SmallTest
    public void testLogoutButton(){

        Intents.init();
        Intent resultData = new Intent();
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
        intending(toPackage("com.example.mid_year_registration")).respondWith(result);
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText(R.string.logout)).perform(click());
        intended(hasComponent(new ComponentName(getTargetContext(), LoginActivity.class)));
        Intents.release();
    }

}
