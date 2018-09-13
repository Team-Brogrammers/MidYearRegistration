package com.example.mid_year_registration;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.SmallTest;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
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

        onView(withId(R.id.stdNoEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.etCourse)).check(matches(isDisplayed()));

        onView(withId(R.id.addImageFab)).check(matches(isDisplayed()));
        onView(withId(R.id.convertImageFab)).check(matches(isDisplayed()));
        onView(withId(R.id.nextFab)).check(matches(isDisplayed()));

    }

    @SmallTest
    public void testValidInput(){
        onView(withId(R.id.stdNoEditText)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.etCourse)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.convertImageFab)).perform(click());
    }

    @SmallTest
    public void testValidInput1(){
        onView(withId(R.id.stdNoEditText)).perform(typeText("1234"), closeSoftKeyboard());
        onView(withId(R.id.etCourse)).perform(typeText("math"), closeSoftKeyboard());
        onView(withId(R.id.convertImageFab)).perform(click());
    }

    @SmallTest
    public void testValidInput2(){
        onView(withId(R.id.stdNoEditText)).perform(typeText("123467"), closeSoftKeyboard());
        onView(withId(R.id.etCourse)).perform(typeText("Math3008"), closeSoftKeyboard());
        onView(withId(R.id.convertImageFab)).perform(click());
    }

    @SmallTest
    public void testValidInput3(){
        onView(withId(R.id.stdNoEditText)).perform(typeText("1234567"), closeSoftKeyboard());
        onView(withId(R.id.etCourse)).perform(typeText("MATH3001"), closeSoftKeyboard());
        onView(withId(R.id.convertImageFab)).perform(click());
    }

    @SmallTest
    public void testValidInput4(){
        onView(withId(R.id.stdNoEditText)).perform(typeText("1234"), closeSoftKeyboard());
        onView(withId(R.id.etCourse)).perform(typeText("MATH3005"), closeSoftKeyboard());
        onView(withId(R.id.convertImageFab)).perform(click());
    }

    @SmallTest
    public void testValidInput5(){
        onView(withId(R.id.stdNoEditText)).perform(typeText("1234567"), closeSoftKeyboard());
        onView(withId(R.id.etCourse)).perform(typeText("Math3005"), closeSoftKeyboard());
        onView(withId(R.id.convertImageFab)).perform(click());
    }

    @SmallTest
    public void testValidInput6(){
        onView(withId(R.id.stdNoEditText)).perform(typeText("1234"), closeSoftKeyboard());
        onView(withId(R.id.etCourse)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.convertImageFab)).perform(click());
    }
    @SmallTest
    public void testValidInput7(){
        onView(withId(R.id.stdNoEditText)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.etCourse)).perform(typeText("MATH3005"), closeSoftKeyboard());
        onView(withId(R.id.convertImageFab)).perform(click());
    }

    @Test
    public void activityResult_DisplaysContactsPhoneNumber() {
        // Build the result to return when the activity is launched.
        Intent resultData = new Intent();
       // EditText stdNo=;
        String filename = "1153631_MATH3001";
        resultData.putExtra("phone", filename);
        Instrumentation.ActivityResult result =
                new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);

        // Set up result stubbing when an intent sent to "contacts" is seen.
        intending(toPackage("com.android.camera")).respondWith(result);

        // User action that results in "contacts" activity being launched.
        // Launching activity expects phoneNumber to be returned and displayed.
        onView(withId(R.id.addImageFab)).perform(click());

        // Assert that the data we set up above is shown.
        onView(withId(R.id.fileName)).check(matches(withText(filename)));
        //onView(withId(R.id.fileName)).perform(setName(filename), ;
    }

}
